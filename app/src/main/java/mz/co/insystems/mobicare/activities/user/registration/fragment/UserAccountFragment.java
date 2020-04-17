package mz.co.insystems.mobicare.activities.user.registration.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.activities.user.registration.UserRegistrationActivity;
import mz.co.insystems.mobicare.activities.user.registration.fragment.presenter.UserAccountFragmentEventHandlerImpl;
import mz.co.insystems.mobicare.activities.user.registration.fragment.view.UserAccountFragmentView;

import mz.co.insystems.mobicare.common.FragmentChangeListener;
import mz.co.insystems.mobicare.common.SyncStatus;
import mz.co.insystems.mobicare.databinding.FragmentUserAccountBinding;
import mz.co.insystems.mobicare.model.user.User;
import mz.co.insystems.mobicare.sync.MobicareSyncService;
import mz.co.insystems.mobicare.sync.SyncError;
import mz.co.insystems.mobicare.sync.VolleyResponseListener;
import mz.co.insystems.mobicare.util.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class UserAccountFragment extends Fragment implements UserAccountFragmentView, Runnable {

    private boolean isUserNameAvalable;
    private boolean noSyncError;
    private Thread userCheckThread;

    private Utilities utilities;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.utilities = Utilities.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentUserAccountBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_account, container, false);
        View view = binding.getRoot();

        UserAccountFragmentEventHandlerImpl userAccountFragmentEventHandler = new UserAccountFragmentEventHandlerImpl(UserAccountFragment.this, getMyActivity().getmUserDao());


        if (getMyActivity().getCurrentUser() == null) getMyActivity().setCurrentUser(new User());

        binding.setUser(getMyActivity().getCurrentUser());
        binding.setPresenter(userAccountFragmentEventHandler);

        return view;
    }

    private UserRegistrationActivity getMyActivity() {
        return (UserRegistrationActivity) getActivity();
    }

    
    public void nextFragment(){
        if (!passwordsMatchAndIsFourCaracteres(getMyActivity().getCurrentUser())) {
            Utilities.displayCommonAlertDialog(getContext(), getString(R.string.passwords_not_match));
        }else if (!passwordIsShort()){
            Utilities.displayCommonAlertDialog(getContext(), getString(R.string.passwords_short));
        }else {
            Fragment fr = new PersonalDataFragment();
            FragmentChangeListener fc = (FragmentChangeListener) getActivity();
            fc.replaceFragment(fr);
        }
    }

    private boolean passwordIsShort() {
        return getMyActivity().getCurrentUser().getPassword().length() >= 4;
    }

    private boolean passwordsMatchAndIsFourCaracteres(User currentUser) {
        return currentUser.getPassword().equals(currentUser.getPasswordConfirm()) && currentUser.getPassword().length() >= 4;
    }

    @Override
    public void checkUserNameAvailability(User user) {
        if (Utilities.isNetworkAvailable(getContext())){
            getMyActivity().setCurrentUser(user);
            if (userNameLenghtIsValid()) {
                userCheckThread = new Thread(this);
                userCheckThread.start();
            }else Utilities.displayCommonAlertDialog(getContext(), getString(R.string.user_name_short));
        }else {
            Utilities.displayCommonAlertDialog(getContext(),getString(R.string.network_not_available));
        }
    }

    private void proccedWithUserNameCheck(User user) {

            Uri.Builder uri = getMyActivity().getService().initServiceUri();
            uri.appendPath(User.TABLE_NAME);
            uri.appendPath(MobicareSyncService.SERVICE_CHECK_USER_NAME_AVAILABILITY);
            uri.appendPath(user.getUserName());
            final String url = uri.build().toString();

            getMyActivity().getService().makeJsonObjectRequest(Request.Method.GET, url, null, getMyActivity().getCurrentUser(), new VolleyResponseListener() {
                @Override
                public void onError(SyncError error) {
                    setNoSyncError(true);
                }

                @Override
                public void onResponse(JSONObject response, int myStatusCode) {
                    try {
                        SyncStatus syncStatus = utilities.fromJsonObject(response, SyncStatus.class);
                        if (syncStatus.getCode() == 100){
                            setUserNameAvalable(true);
                        } else {
                            setUserNameAvalable(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onResponse(JSONArray response, int myStatusCode) {
                }
            });


    }

    private boolean userNameLenghtIsValid() {
        return getMyActivity().getCurrentUser().getUserName().length() >=4;
    }


    public void cancelOperation() {

    }

    @Override
    public void run() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getMyActivity().showLoading(getContext(), null, getString(R.string.checking_user_name_availability));
            }
        });

        proccedWithUserNameCheck(getMyActivity().getCurrentUser());
        try {
            userCheckThread.join(getMyActivity().getService().TWENTY_SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getMyActivity().hideLoading();
                nextFragment();
                /*if (getMyActivity().noSyncError()){
                    if (getMyActivity().syncOperationDone()){
                        if (isUserNameAvalable()) nextFragment();
                        else Utilities.displayCommonAlertDialog(getContext(), getString(R.string.user_name_is_taken));
                    } else Utilities.displayCommonAlertDialog(getContext(), getString(R.string.sync_time_out));
                } else Utilities.displayCommonAlertDialog(getContext(), getString(R.string.error_msg_sync_failed));*/
            }
        });

    }

    public boolean isUserNameAvalable() {
        return isUserNameAvalable;
    }

    public void setUserNameAvalable(boolean userNameAvalable) {
        isUserNameAvalable = userNameAvalable;
    }

    public boolean isNoSyncError() {
        return noSyncError;
    }

    public void setNoSyncError(boolean noSyncError) {
        this.noSyncError = noSyncError;
    }
}

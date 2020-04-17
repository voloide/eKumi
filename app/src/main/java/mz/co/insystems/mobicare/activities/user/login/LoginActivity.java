package mz.co.insystems.mobicare.activities.user.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.j256.ormlite.dao.Dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.activities.search.SearchActivity;
import mz.co.insystems.mobicare.activities.user.registration.UserRegistrationActivity;
import mz.co.insystems.mobicare.base.BaseActivity;
import mz.co.insystems.mobicare.databinding.ActivityLoginBinding;
import mz.co.insystems.mobicare.model.user.User;
import mz.co.insystems.mobicare.sync.MobicareSyncService;
import mz.co.insystems.mobicare.sync.ServiceResponseListener;
import mz.co.insystems.mobicare.sync.SyncError;
import mz.co.insystems.mobicare.sync.VolleyResponseListener;
import mz.co.insystems.mobicare.util.Utilities;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends BaseActivity implements LoginActions, Runnable {

    private ActivityLoginBinding activityLoginBinding;
    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;
    private  boolean keybordOpen = false;
    private int monitorNumber =0;
    private Thread loginThread;
    private Dao.CreateOrUpdateStatus createOrUpdateStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginManager loginManager = new LoginManager(LoginActivity.this);

        if (getCurrentUser() == null){
            setCurrentUser(new User());
        }

        activityLoginBinding.setUser(getCurrentUser());
        activityLoginBinding.setPresenter(loginManager);

        attachKeyboardListeners();
    }




    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

            int viewHeight= activityLoginBinding.getRoot().getRootView().getHeight();

            int rootHight = activityLoginBinding.getRoot().getHeight();



            int heightDiff = activityLoginBinding.getRoot().getRootView().getHeight() - activityLoginBinding.getRoot().getHeight();


            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(LoginActivity.this);

            if (monitorNumber == 0 && rootHight > monitorNumber){
                monitorNumber = rootHight;
                onShowKeyboard();
            }else if (monitorNumber > 0 && rootHight > monitorNumber){
                monitorNumber = rootHight;
                onHideKeyboard();
            }else if (monitorNumber > 0 && rootHight < monitorNumber){
                monitorNumber = rootHight;
                onShowKeyboard();
            }


        }
    };

    protected void onShowKeyboard() {
        this.keybordOpen = true;
        activityLoginBinding.setLoginActivity(this);
    }
    protected void onHideKeyboard() {
        this.keybordOpen = false;
        activityLoginBinding.setLoginActivity(this);
    }

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLayout = (ViewGroup) activityLoginBinding.getRoot();
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    @Override
    public void doLogin(final User user) {
        try {
            setCurrentUser(user);
            if (!areUsersOnDB()){
                if (Utilities.isNetworkAvailable(LoginActivity.this)) {
                    loginThread = new Thread(this);
                    loginThread.start();
                } else
                    Utilities.displayCommonAlertDialog(LoginActivity.this, getString(R.string.internet_not_available));
            }else {
                showLoading(LoginActivity.this,null, getString(R.string.checking_credential));
                //user.enCryptPassword();
                if (getmUserDao().isAuthentic(user)) {
                    setCurrentUser(getmUserDao().getByCredencials(user));
                    if (Utilities.isNetworkAvailable(LoginActivity.this)) {
                        loginThread = new Thread(this);
                        loginThread.start();
                    }
                    hideLoading();
                    redirectToSearch();
                } else {
                    hideLoading();
                    Utilities.displayCommonAlertDialog(LoginActivity.this, getString(R.string.user_password_invalid));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean areUsersOnDB() throws SQLException {
        return Utilities.listHasElements(getmUserDao().queryForAll());
    }

    private void tryToGetUserByCredencials(User currentUser) {
        getUserFromWeb();
    }

    private void getUserFromWeb(){

        Uri.Builder uri =  getService().initServiceUri();
        uri.appendPath(User.TABLE_NAME);
        uri.appendPath(MobicareSyncService.URL_SERVICE_USER_GET_BY_CREDENTIALS);
        uri.appendPath(getCurrentUser().getUserName());
        uri.appendPath(Utilities.MD5Crypt(getCurrentUser().getPassword()));
        final String url = uri.build().toString();

        ServiceResponseListener responseListener = new ServiceResponseListener();
        try {
            getService().makeJsonObjectRequest(Request.Method.GET, url, getCurrentUser().parseToJsonObject(), getCurrentUser(), new VolleyResponseListener() {
                @Override
                public void onError(SyncError error) {

                }

                @Override
                public void onResponse(JSONObject response, int myStatusCode) {
                    try {
                        User user = utilities.fromJsonObject(response, User.class);
                        createOrUpdateStatus = getmUserDao().createOrUpdate(user);
                        setCurrentUser(user);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onResponse(JSONArray response, int myStatusCode) {}
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void redirectToSearch() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(User.TABLE_NAME, getCurrentUser());

        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void tryToUpdateLoginStatusOnWeb(User user) {

        Uri.Builder uri = service.initServiceUri();
        uri.appendPath(MobicareSyncService.SERVICE_ENTITY_USER);
        uri.appendPath(MobicareSyncService.SERVICE_AUTHENTICATE);
        final String url = uri.build().toString();

        service.makeJsonObjectRequest(Request.Method.POST, url, null, user, new VolleyResponseListener() {

            @Override
            public void onError(SyncError error) {

            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {}

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {}
        });
    }

    @Override
    public void initNewUserCreation() {
        Intent intent = new Intent(getApplicationContext(), UserRegistrationActivity.class);
        intent.putExtra(User.TABLE_NAME, getCurrentUser());
        startActivity(intent);
    }

    @Override
    public void initPasswordReset() {

    }

    @Override
    public int getMarginDimension() {
        return keybordOpen ? R.dimen.dimen_20dp : R.dimen.dimen_50dp;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (keyboardListenersAttached) {
            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
    }


    public boolean isKeybordOpen() {
        return keybordOpen;
    }

    public void setKeybordOpen(boolean keybordOpen) {
        this.keybordOpen = keybordOpen;
    }

    @Override
    public void run() {
        try {
            if (areUsersOnDB()){
                tryToUpdateLoginStatusOnWeb(getCurrentUser());
            }else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showLoading(LoginActivity.this,null, getString(R.string.loading_user_data));
                    }
                });
                tryToGetUserByCredencials(getCurrentUser());
                loginThread.join(MobicareSyncService.TWENTY_SECONDS);


                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          hideLoading();
                          if (createOrUpdateStatus != null) {

                              if (createOrUpdateStatus.isCreated() || createOrUpdateStatus.isUpdated()) {
                                  redirectToSearch();
                              } else
                                  Utilities.displayCommonAlertDialog(LoginActivity.this, getString(R.string.user_password_invalid));
                          }else
                              Utilities.displayCommonAlertDialog(LoginActivity.this, getString(R.string.sync_time_out));
                      }
                  });

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

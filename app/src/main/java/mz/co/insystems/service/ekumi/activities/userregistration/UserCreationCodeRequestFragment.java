package mz.co.insystems.service.ekumi.activities.userregistration;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.common.FragmentChangeListener;
import mz.co.insystems.service.ekumi.databinding.UserCreationCodeRequestBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserCreationCodeRequestFragment extends Fragment {


    public UserCreationCodeRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        UserCreationCodeRequestBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_creation_code_request, container, false);


        binding.setViewmodel(getMyActivity().getViewModel());

        getMyActivity().setCodeRequestFragment(this);

        return binding.getRoot();
    }

    public UserRegistrationActivity getMyActivity() {
        return (UserRegistrationActivity) getActivity();
    }

    public void nextFragment(){
        Fragment fr = new UserRegistrationFragment();
        FragmentChangeListener fc = (FragmentChangeListener) getActivity();
        fc.replaceFragment(fr);
    }
}

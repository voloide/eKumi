package mz.co.insystems.service.ekumi.activities.userregistration;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.databinding.RegistrationFragmentBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRegistrationFragment extends Fragment {


    public UserRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RegistrationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_registration, container, false);
        binding.setViewmodel(getMyActivity().getViewModel());
        getMyActivity().setRegistrationFragment(this);
        return binding.getRoot();
    }

    public UserRegistrationActivity getMyActivity() {
        return (UserRegistrationActivity) getActivity();
    }

}

package mz.co.insystems.service.ekumi.activities.pinreset;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.common.FragmentChangeListener;
import mz.co.insystems.service.ekumi.databinding.CodeRequestBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordResetCodeRequestFragment extends Fragment {


    private PasswordResetViewModel viewModel;

    public PasswordResetCodeRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CodeRequestBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_reset_code_request, container, false);

        viewModel = new PasswordResetViewModel(getMyActivity().getCurrentUser(), getMyActivity());
        binding.setViewmodel(this.viewModel);

        getMyActivity().setPasswordResetCodeRequestFragment(this);

        return binding.getRoot();
    }

    public PasswordResetActivity getMyActivity() {
        return (PasswordResetActivity) getActivity();
    }

    public void nextFragment(){
        Fragment fr = new PasswordResetFragment();
        FragmentChangeListener fc = (FragmentChangeListener) getActivity();
        fc.replaceFragment(fr);
    }
}

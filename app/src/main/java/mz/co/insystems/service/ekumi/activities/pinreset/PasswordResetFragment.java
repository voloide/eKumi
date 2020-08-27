package mz.co.insystems.service.ekumi.activities.pinreset;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.databinding.PasswordResetFragmentBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordResetFragment extends Fragment {

    private PasswordResetViewModel viewModel;

    public PasswordResetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PasswordResetFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_reset, container, false);

        this.viewModel = new PasswordResetViewModel(getMyActivity().getCurrentUser(), getMyActivity());

        getMyActivity().setPasswordResetFragment(this);

        binding.setViewmodel(this.viewModel);

        return binding.getRoot();
    }

    private PasswordResetActivity getMyActivity() {
        return (PasswordResetActivity) getActivity();
    }


}

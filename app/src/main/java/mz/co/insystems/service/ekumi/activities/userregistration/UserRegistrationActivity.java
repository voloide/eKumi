package mz.co.insystems.service.ekumi.activities.userregistration;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.base.AbstractBaseActivity;
import mz.co.insystems.service.ekumi.base.AbstractBaseViewModel;
import mz.co.insystems.service.ekumi.common.FragmentChangeListener;
import mz.co.insystems.service.ekumi.model.user.User;
import mz.co.insystems.service.ekumi.model.user.service.UserService;

public class UserRegistrationActivity extends AbstractBaseActivity implements FragmentChangeListener {

    private FragmentManager manager;
    private UserService userService;
    private UserCreationCodeRequestFragment codeRequestFragment;
    private UserRegistrationFragment registrationFragment;
    private UserRegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_creation);


        this.userService = UserService.getInstance(getApplicationContext());

        this.viewModel = new UserRegistrationViewModel(getCurrentUser(), this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUser();

        if (savedInstanceState == null){
            this.codeRequestFragment = new UserCreationCodeRequestFragment();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.root_container, codeRequestFragment);
            transaction.commit();
        }
    }

    private void initUser() {
        setCurrentUser(new User());
    }

    @Override
    protected AbstractBaseViewModel initViewModel() {
        return new UserRegistrationViewModel(getCurrentUser(), this);
    }


    public UserService getUserService() {
        return userService;
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.user_creation_root_container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    public UserCreationCodeRequestFragment getCodeRequestFragment() {
        return codeRequestFragment;
    }

    public void setCodeRequestFragment(UserCreationCodeRequestFragment codeRequestFragment) {
        this.codeRequestFragment = codeRequestFragment;
    }

    public UserRegistrationFragment getRegistrationFragment() {
        return registrationFragment;
    }

    public void setRegistrationFragment(UserRegistrationFragment registrationFragment) {
        this.registrationFragment = registrationFragment;
    }

    public UserRegistrationViewModel getViewModel() {
        return viewModel;
    }
}

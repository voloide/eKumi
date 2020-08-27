package mz.co.insystems.service.ekumi.activities.pinreset;

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

public class PasswordResetActivity extends AbstractBaseActivity implements FragmentChangeListener {

    private FragmentManager manager;
    private PasswordResetCodeRequestFragment passwordResetCodeRequestFragment;
    private PasswordResetFragment passwordResetFragment;
    private UserService userService;

    public PasswordResetActivity() {
        this.manager = getSupportFragmentManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_container);

        this.userService = UserService.getInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUser();

        if (savedInstanceState == null){
            this.passwordResetCodeRequestFragment = new PasswordResetCodeRequestFragment();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.root_container, passwordResetCodeRequestFragment);
            transaction.commit();
        }
    }

    private void initUser() {
        setCurrentUser(new User());
    }

    @Override
    protected AbstractBaseViewModel initViewModel() {
        return null;
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.root_container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    public void setPasswordResetCodeRequestFragment(PasswordResetCodeRequestFragment passwordResetCodeRequestFragment) {
        this.passwordResetCodeRequestFragment = passwordResetCodeRequestFragment;
    }

    public void setPasswordResetFragment(PasswordResetFragment passwordResetFragment) {
        this.passwordResetFragment = passwordResetFragment;
    }

    public PasswordResetCodeRequestFragment getPasswordResetCodeRequestFragment() {
        return passwordResetCodeRequestFragment;
    }

    public PasswordResetFragment getPasswordResetFragment() {
        return passwordResetFragment;
    }

    public UserService getUserService() {
        return userService;
    }
}

package mz.co.insystems.mobicare.activities.user.registration;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.activities.user.registration.fragment.UserAccountFragment;
import mz.co.insystems.mobicare.activities.user.registration.fragment.presenter.UserRegistrationFragmentEventHandler;
import mz.co.insystems.mobicare.base.BaseActivity;
import mz.co.insystems.mobicare.common.FragmentChangeListener;

public class UserRegistrationActivity extends BaseActivity implements UserRegistrationFragmentEventHandler, FragmentChangeListener {

    private FragmentManager manager;
    private UserRegistrationFragmentEventHandler presenter;

    public UserRegistrationActivity() {
        super();
        this.manager = getSupportFragmentManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            UserAccountFragment accountFragment = new UserAccountFragment();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.root_container, accountFragment);
            transaction.commit();
        }
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.root_container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

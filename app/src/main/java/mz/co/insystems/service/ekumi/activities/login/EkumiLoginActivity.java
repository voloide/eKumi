package mz.co.insystems.service.ekumi.activities.login;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.sql.SQLException;

import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.activities.pinreset.PasswordResetActivity;
import mz.co.insystems.service.ekumi.activities.search.SearchActivity;
import mz.co.insystems.service.ekumi.activities.userregistration.UserRegistrationActivity;
import mz.co.insystems.service.ekumi.base.AbstractBaseActivity;
import mz.co.insystems.service.ekumi.base.AbstractBaseViewModel;
import mz.co.insystems.service.ekumi.databinding.EkumiLoginActivityBinding;
import mz.co.insystems.service.ekumi.model.user.User;
import mz.co.insystems.service.ekumi.model.user.service.UserService;
import mz.co.insystems.service.ekumi.util.Utilities;

public class EkumiLoginActivity extends AbstractBaseActivity {

    private EkumiLoginActivityBinding ekumiLoginActivityBinding;
    private UserService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ekumiLoginActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_ekumi_login);

        this.userService = UserService.getInstance(getApplicationContext());

        if (getCurrentUser() == null){  setCurrentUser(new User()); }

        ekumiLoginActivityBinding.setViewmodel((LoginViewModel) viewModel);
    }

    public AbstractBaseViewModel initViewModel() {
        return new LoginViewModel(getCurrentUser(), this);
    }



    public void doLogin(User user) {
        try {
            setCurrentUser(user);
            if (!areUsersOnDB()){
                if (Utilities.isNetworkAvailable(EkumiLoginActivity.this)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLoading(EkumiLoginActivity.this,null, getString(R.string.loading_user_data));
                        }
                    });
                    user = this.userService.getUserFromWebByCredentials(getCurrentUser());
                    hideLoading();

                    if (user == null){
                        Utilities.displayAlertDialog(EkumiLoginActivity.this, getApplicationContext().getString(R.string.user_password_invalid));
                    }else {
                        this.userService.saveUser(this.userService.getCurrUser());
                        redirectToSearch();
                    }
                } else
                    Utilities.displayAlertDialog(EkumiLoginActivity.this, getString(R.string.internet_not_available));
            }else {
                showLoading(EkumiLoginActivity.this,null, getString(R.string.checking_credential));
                //user.enCryptPassword();
                if (userService.isAuthentic(user)) {
                    setCurrentUser(userService.getByCredencials(user));
                    if (Utilities.isNetworkAvailable(EkumiLoginActivity.this)) {
                        //loginThread = new Thread(this);
                        //loginThread.start();
                    }
                    hideLoading();
                    redirectToSearch();
                } else {
                    hideLoading();
                    Utilities.displayAlertDialog(EkumiLoginActivity.this, getString(R.string.user_password_invalid));
                }
            }
        } catch (SQLException e) {
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

    private boolean areUsersOnDB() throws SQLException {
        return userService.areUsersOnDB();
    }


    public void initNewUserCreation() {
        Intent intent = new Intent(getApplicationContext(), UserRegistrationActivity.class);
        intent.putExtra(User.TABLE_NAME, getCurrentUser());
        startActivity(intent);
    }


    public void initPasswordReset() {
        Intent intent = new Intent(getApplicationContext(), PasswordResetActivity.class);
        startActivity(intent);
    }

    /**
     * Sets focus on a specific EditText field.
     *
     * @param editText EditText to set focus on
     */
    public static void setFocus(EditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }

    /**
     * Hides soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * Shows soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }



    /**
     * Sets focused PIN field background.
     *
     * @param editText edit text to change
     */
    private void setFocusedPinBackground(EditText editText) {
        setViewBackground(editText, getResources().getDrawable(R.drawable.pin_edit_text_focused));
    }

    private void setDefaultPinBackground(EditText editText) {
        setViewBackground(editText, getResources().getDrawable(R.drawable.pin_edit_text));
    }

    public void setViewBackground(View view, Drawable background) {
        if (view == null || background == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }
}

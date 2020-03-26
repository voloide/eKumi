package mz.co.insystems.mobicare.activities.user.login;

import mz.co.insystems.mobicare.model.user.User;

/**
 * Created by Voloide Tamele on 3/16/2018.
 */

public class LoginManager {

    private LoginActions loginActions;

    public LoginManager(LoginActions loginActions) {
        this.loginActions = loginActions;
    }

    public void login(User user){
        loginActions.doLogin(user);
    }

    public int getMarginDimension(){
        return loginActions.getMarginDimension();
    }

    public void initNewUserCreation(){
        loginActions.initNewUserCreation();
    }

    public void initPasswordReset(){
        loginActions.initPasswordReset();
    }
}

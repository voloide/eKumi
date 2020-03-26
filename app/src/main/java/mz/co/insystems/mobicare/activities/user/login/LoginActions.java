package mz.co.insystems.mobicare.activities.user.login;

import mz.co.insystems.mobicare.model.user.User;

/**
 * Created by Voloide Tamele on 3/16/2018.
 */

interface LoginActions {

    void doLogin(User user);

    void initNewUserCreation();

    void initPasswordReset();

    int getMarginDimension();
}

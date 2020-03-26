package mz.co.insystems.mobicare.activities.user.registration.fragment.presenter;

import mz.co.insystems.mobicare.activities.user.registration.fragment.view.UserRegistrationView;
import mz.co.insystems.mobicare.model.user.UserDao;

/**
 * Created by Voloide Tamele on 3/16/2018.
 */

public class UserRegistrationFragmentEventHandlerImpl implements UserRegistrationFragmentEventHandler {

    UserRegistrationView userRegistrationView;
    UserDao userDao;

    public UserRegistrationFragmentEventHandlerImpl(UserRegistrationView userRegistrationView, UserDao userDao) {
        this.userRegistrationView = userRegistrationView;
        this.userDao = userDao;
    }


    public void showLoading() {

    }

    public void hideLoading() {

    }
}

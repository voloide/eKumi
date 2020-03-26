package mz.co.insystems.mobicare.activities.user.registration.fragment.presenter;

import mz.co.insystems.mobicare.activities.user.registration.fragment.view.UserAccountFragmentView;
import mz.co.insystems.mobicare.model.user.User;
import mz.co.insystems.mobicare.model.user.UserDao;

/**
 * Created by Voloide Tamele on 3/19/2018.
 */

public class UserAccountFragmentEventHandlerImpl implements UserAccountFragmentEventHandler {

    private UserAccountFragmentView userAccountFragmentView;
    private UserDao userDao;

    public UserAccountFragmentEventHandlerImpl (UserAccountFragmentView userAccountFragmentView, UserDao userDao) {
        this.userAccountFragmentView = userAccountFragmentView;
        this.userDao = userDao;
    }

    public void nextFragment(User user) {
        userAccountFragmentView.checkUserNameAvailability(user);
    }

    public void cancelOperation() {

    }
}

package mz.co.insystems.service.ekumi.activities.login;

import androidx.databinding.Bindable;

import mz.co.insystems.service.ekumi.BR;
import mz.co.insystems.service.ekumi.base.AbstractBaseViewModel;
import mz.co.insystems.service.ekumi.model.user.User;

public class LoginViewModel extends AbstractBaseViewModel {
    private User user;

    public LoginViewModel(User user, EkumiLoginActivity ekumiLoginActivity) {
        super(ekumiLoginActivity);
        this.user = user;
    }

    @Bindable
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        notifyPropertyChanged(BR.user);
    }

    @Override
    public EkumiLoginActivity getRelatedActivity() {
        return (EkumiLoginActivity) super.getRelatedActivity();
    }

    public void login(){
        getRelatedActivity().doLogin(user);
    }

    public void initNewUserCreation(){
        getRelatedActivity().initNewUserCreation();
    }

    public void initPasswordReset(){
        getRelatedActivity().initPasswordReset();
    }
}

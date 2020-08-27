package mz.co.insystems.service.ekumi.activities.userregistration;

import androidx.databinding.Bindable;

import java.sql.SQLException;

import mz.co.insystems.service.ekumi.BR;
import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.base.AbstractBaseViewModel;
import mz.co.insystems.service.ekumi.model.user.User;
import mz.co.insystems.service.ekumi.model.user.service.UserService;

public class UserRegistrationViewModel extends AbstractBaseViewModel {

    private User user;
    private String pinConfirm;
    private String codigo;

    public UserRegistrationViewModel(User user, UserRegistrationActivity relatedActivity) {
        super(relatedActivity);
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

    public void save(){
        getRelatedActivity().showLoading(this.getOperationService().getContext(), "Loading", this.getOperationService().getContext().getString(R.string.checking_user_name_availability));
        this.getOperationService().isMobileNumberInUse(this.user);

        try {
            this.getOperationService().saveUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private UserService getOperationService(){
        return (UserService) getRelatedActivity().getOperationService();
    }

    @Override
    public UserRegistrationActivity getRelatedActivity() {
        return (UserRegistrationActivity) super.getRelatedActivity();
    }

    @Bindable
    public String getPinConfirm() {
        return pinConfirm;
    }

    public void setPinConfirm(String pinConfirm) {
        this.pinConfirm = pinConfirm;
        notifyPropertyChanged(BR.pinConfirm);
    }
    @Bindable
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
        notifyPropertyChanged(BR.codigo);
    }

    public void nextFragment(){
        //isMobileNumberInUse();
        getRelatedActivity().getCodeRequestFragment().nextFragment();
    }
}

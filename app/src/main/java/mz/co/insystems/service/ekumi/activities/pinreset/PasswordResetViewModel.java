package mz.co.insystems.service.ekumi.activities.pinreset;

import android.content.Context;

import androidx.databinding.Bindable;


import mz.co.insystems.service.ekumi.BR;
import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.base.AbstractBaseViewModel;
import mz.co.insystems.service.ekumi.model.user.User;
import mz.co.insystems.service.ekumi.model.user.service.UserService;
import mz.co.insystems.service.ekumi.util.Utilities;

public class PasswordResetViewModel extends AbstractBaseViewModel {

    private User user;
    private String codigo;

    private String pinconfirm;

    public PasswordResetViewModel(User user, PasswordResetActivity relatedActivity) {
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

    private UserService getOperationService(){
        return (UserService) getRelatedActivity().getOperationService();
    }

    @Override
    public PasswordResetActivity getRelatedActivity() {
        return (PasswordResetActivity) super.getRelatedActivity();
    }

    public void nextFragment(){
        //isMobileNumberInUse();
        getRelatedActivity().getPasswordResetCodeRequestFragment().nextFragment();
    }

    public void checkMobileNumber(){
        getRelatedActivity().showLoading(getApplicationContext(), null, getApplicationContext().getString(R.string.checking_user_name_availability));
        boolean result = getRelatedActivity().getUserService().isMobileNumberInUse(this.user);

        getRelatedActivity().hideLoading();

        if (getRelatedActivity().getUserService().getWebService().isResourdeAvailable()){
            Utilities.displayAlertDialog(getRelatedActivity(), getApplicationContext().getString(R.string.code_request_msg));
            getRelatedActivity().getUserService().sendConfirmationCode();
            getRelatedActivity().getPasswordResetCodeRequestFragment().nextFragment();
        }else
        if (getRelatedActivity().getUserService().getWebService().isResourdeNotAvailable()){
            Utilities.displayAlertDialog(getRelatedActivity(), getApplicationContext().getString(R.string.invalid_number));
        }else
        if (result){
            Utilities.displayAlertDialog(getRelatedActivity(), getApplicationContext().getString(R.string.number_taken));
        }
    }

    private Context getApplicationContext() {
        return getRelatedActivity().getApplicationContext();
    }

    public void reset(){
        if (this.user.getPin() != this.pinconfirm){
            Utilities.displayAlertDialog(getRelatedActivity(), getApplicationContext().getString(R.string.diferent_pins));
        }else {
            getRelatedActivity().getUserService().confirUserCode();
            getRelatedActivity().getUserService().resetPin(this.user);
        }
    }

    @Bindable
    public String getPinconfirm() {
        return pinconfirm;
    }

    public void setPinconfirm(String pinconfirm) {
        this.pinconfirm = pinconfirm;
        notifyPropertyChanged(BR.pinconfirm);

    }

    @Bindable
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
        notifyPropertyChanged(BR.codigo);
    }
}

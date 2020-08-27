package mz.co.insystems.service.ekumi.base;

import androidx.databinding.BaseObservable;

import mz.co.insystems.service.ekumi.util.Utilities;

public abstract class AbstractBaseViewModel extends BaseObservable {

    protected AbstractBaseActivity relatedActivity;

    protected Utilities utilities;

    public AbstractBaseViewModel(AbstractBaseActivity relatedActivity) {
        this.relatedActivity = relatedActivity;
        utilities = Utilities.getInstance();
    }

    public AbstractBaseActivity getRelatedActivity() {
        return relatedActivity;
    }

}

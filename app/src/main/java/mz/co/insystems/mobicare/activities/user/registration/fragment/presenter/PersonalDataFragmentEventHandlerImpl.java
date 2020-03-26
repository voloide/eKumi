package mz.co.insystems.mobicare.activities.user.registration.fragment.presenter;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.activities.user.registration.fragment.PersonalDataFragment;
import mz.co.insystems.mobicare.activities.user.registration.fragment.view.PersonalDataFragmentView;
import mz.co.insystems.mobicare.model.endereco.localizacao.Localizacao;
import mz.co.insystems.mobicare.model.user.User;
import mz.co.insystems.mobicare.model.user.UserDao;

/**
 * Created by Voloide Tamele on 3/19/2018.
 */

public class PersonalDataFragmentEventHandlerImpl implements PersonalDataFragmentEventHandler {
    private PersonalDataFragmentView personalDataFragmentView;
    private UserDao userDao;

    public PersonalDataFragmentEventHandlerImpl(PersonalDataFragmentView personalDataFragmentView, UserDao userDao) {
        this.personalDataFragmentView = personalDataFragmentView;
        this.userDao = userDao;
    }



    public void doSave(User user) {
       personalDataFragmentView.doSave(user);
    }


    private PersonalDataFragment getPersonalDataFragment(){
        return (PersonalDataFragment) personalDataFragmentView;
    }

    private Localizacao getLocalizacao(){
        return ((PersonalDataFragment) personalDataFragmentView).getLocalizacao();
    }


    private boolean isRuralSelected(String tipoLocalizacao){
        return getPersonalDataFragment().getActivity().getString(R.string.rural).equalsIgnoreCase(tipoLocalizacao);
    }
    @Override
    public void onTipoLocalizacaoClick(String tipoLocalizacao) {
        if (isRuralSelected(tipoLocalizacao)){
            personalDataFragmentView.initRuralLocalizacao();
        }else
            personalDataFragmentView.initMunicipalLocalizacao();
    }
}

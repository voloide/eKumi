package mz.co.insystems.mobicare.activities.user.registration.fragment.view;

import mz.co.insystems.mobicare.model.endereco.localizacao.Localizacao;
import mz.co.insystems.mobicare.model.user.User;

/**
 * Created by Voloide Tamele on 3/19/2018.
 */

public interface PersonalDataFragmentView {

    void initMunicipalLocalizacao();
    void initRuralLocalizacao();
    void onTipoLocalizacaoClick(String tipoLocalizacao);
    void showLoading();
    void hideLoading();
    void loadLevelOneAdapters(Localizacao localizacao);
    void loadLevelTwoAdapters(Localizacao localizacao);
    void loadLevelThreeAdapters(Localizacao localizacao);

    void reloadAllAdapters(Localizacao localizacao);

    void setLocalizacao(Localizacao localizacao);

    User getCurrentUser();

    void doSave(User user);
}

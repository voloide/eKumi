package mz.co.insystems.mobicare.model.endereco.localizacao;

/**
 * Created by Voloide Tamele on 3/16/2018.
 */

interface LocalizacaoSync {

    void syncProvincias();
    void syncDistritos();
    void syncPostos();
    void syncMunicipios();
    void syncBairros();

}

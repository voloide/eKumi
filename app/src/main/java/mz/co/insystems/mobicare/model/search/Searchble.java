package mz.co.insystems.mobicare.model.search;

import mz.co.insystems.mobicare.model.contacto.Contacto;
import mz.co.insystems.mobicare.model.endereco.Endereco;

/**
 * Created by Voloide Tamele on 4/16/2018.
 */

public interface Searchble {
    String getDesignacao();
    int getDisponibilidade();
    double getPreco();
    String getFarmaciaName();
    String getDistancia();
    String getImage();
}

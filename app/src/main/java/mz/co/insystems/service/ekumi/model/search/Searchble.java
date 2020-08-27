package mz.co.insystems.service.ekumi.model.search;

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
    boolean isAberto();
    boolean is24h();
    String getHorario();
}

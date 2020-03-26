package mz.co.insystems.mobicare.model.endereco;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.model.endereco.bairro.Bairro;
import mz.co.insystems.mobicare.model.endereco.postoadministrativo.PostoAdministrativo;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = Endereco.TABLE_NAME_ENDERECO, daoClass = EnderecoDaoImpl.class)
public class Endereco extends BaseVO {

    public static final String TABLE_NAME_ENDERECO			        = "endereco";
    public static final String COLUMN_ENDERECO_ID 			        = "id";
    public static final String COLUMN_ENDERECO_LATITUDE 			= "latitude";
    public static final String COLUMN_ENDERECO_LONGITUDE            = "longitude";
    public static final String COLUMN_ENDERECO_BAIRRO_ID 	        = "bairro_id";
    public static final String COLUMN_ENDERECO_POSTO_ID 	        = "posto_id";
    public static final String COLUMN_ENDERECO_RUA_AV 	            = "ruaAvenida";
    public static final String COLUMN_ENDERECO_ZONA 	            = "zona";
    public static final String COLUMN_ENDERECO_NCASA 	            = "ncasa";

    private static final long serialVersionUID = 1L;

    @DatabaseField(columnName = COLUMN_ENDERECO_ID, id = true, generatedId = false)
    private int id;
    @DatabaseField
    private double latitude;
    @DatabaseField
    private double longitude;
    @DatabaseField
    private String zona;

    @DatabaseField(columnName = COLUMN_ENDERECO_BAIRRO_ID, foreign = true, foreignAutoRefresh = true)
    private Bairro bairro;

    @DatabaseField(columnName = COLUMN_ENDERECO_POSTO_ID, foreign = true, foreignAutoRefresh = true)
    private PostoAdministrativo postoAdministrativo;

    @DatabaseField
    private String ruaAvenida;


    @DatabaseField
    private String ncasa;


    public Endereco(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Endereco(){}

    public Endereco(int id) {
        this.id = id;
    }


    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        notifyPropertyChanged(BR.latitude);
    }
    @Bindable
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        notifyPropertyChanged(BR.longitude);
    }
    @Bindable
    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
        notifyPropertyChanged(BR.zona);
    }
    @Bindable
    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
        notifyPropertyChanged(BR.bairro);
    }
    @Bindable
    public PostoAdministrativo getPostoAdministrativo() {
        return postoAdministrativo;
    }

    public void setPostoAdministrativo(PostoAdministrativo postoAdministrativo) {
        this.postoAdministrativo = postoAdministrativo;
        notifyPropertyChanged(BR.postoAdministrativo);
    }
    @Bindable
    public String getRuaAvenida() {
        return ruaAvenida;
    }

    public void setRuaAvenida(String ruaAvenida) {
        this.ruaAvenida = ruaAvenida;
        notifyPropertyChanged(BR.ruaAvenida);
    }
    @Bindable
    public String getNcasa() {
        return ncasa;
    }

    public void setNcasa(String ncasa) {
        this.ncasa = ncasa;
        notifyPropertyChanged(BR.ncasa);
    }
}

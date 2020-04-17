package mz.co.insystems.mobicare.model.grupofarmaco;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.model.contacto.Contacto;
import mz.co.insystems.mobicare.model.endereco.Endereco;
import mz.co.insystems.mobicare.model.farmacia.Farmacia;
import mz.co.insystems.mobicare.model.farmaco.Farmaco;


/**
 * Created by Voloide Tamele on 10/23/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = GrupoFarmaco.TABLE_NAME_FARMACO, daoClass = GrupoFarmacoDaoImpl.class)
public class GrupoFarmaco extends BaseVO {
    public static final String TABLE_NAME_FARMACO			                = "grupo_farmaco";
    public static final String COLUMN_FARMACO_ID 			                = "id";
    public static final String COLUMN_FARMACO_DESIGNACAO			        = "designacao";
    private static final long serialVersionUID = -5483888414207922057L;


    @DatabaseField(columnName = COLUMN_FARMACO_ID, id = true, generatedId = false)
    private long id;
    @DatabaseField(columnName = COLUMN_FARMACO_DESIGNACAO)
    private String designacao;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private String logo;

    private List<Farmaco> farmacos;

    public GrupoFarmaco() {

    }

    @Bindable
    public String getDescricao() {
        return this.designacao;
    }
    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
        notifyPropertyChanged(BR.designacao);
    }
    @Bindable
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
        notifyPropertyChanged(BR.logo);
    }

    @Bindable
    public List<Farmaco> getFarmacos() {
        return farmacos;
    }

    public void setFarmacos(List<Farmaco> farmacos) {
        this.farmacos = farmacos;
        notifyPropertyChanged(BR.farmacos);
    }
}

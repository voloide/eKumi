package mz.co.insystems.mobicare.model.grupofarmaco;

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

import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.common.JsonParseble;
import mz.co.insystems.mobicare.model.contacto.Contacto;
import mz.co.insystems.mobicare.model.endereco.Endereco;
import mz.co.insystems.mobicare.model.farmacia.Farmacia;


/**
 * Created by Voloide Tamele on 10/23/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = GrupoFarmaco.TABLE_NAME_FARMACO, daoClass = GrupoFarmacoDaoImpl.class)
public class GrupoFarmaco extends BaseVO implements JsonParseble<GrupoFarmaco> {
    public static final String TABLE_NAME_FARMACO			                = "grupo_farmaco";
    public static final String COLUMN_FARMACO_ID 			                = "id";
    public static final String COLUMN_FARMACO_DESIGNACAO			        = "designacao";


    private static final long serialVersionUID = 1L;

    private ObjectMapper objectMapper = new ObjectMapper();

    @DatabaseField(columnName = COLUMN_FARMACO_ID, id = true, generatedId = false)
    private long id;
    @DatabaseField(columnName = COLUMN_FARMACO_DESIGNACAO)
    private String designacao;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] logo;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] image;

    public GrupoFarmaco() {

    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public void setImage(byte[] image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    public String getDescricao() {
        return this.designacao;
    }

    public long getId() {
        return id;
    }

    public Endereco getEndereco() {
        throw new RuntimeException("Metodo nao aplicavel");
    }

    public Contacto getContacto() {
        throw new RuntimeException("Metodo nao aplicavel");
    }

    public Farmacia getFarmacia() {
        throw new RuntimeException("Metodo nao aplicavel");
    }

    public int getDisponibilidade() {
        throw new RuntimeException("Metodo nao aplicavel");
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }


    @Override
    public JSONObject toJsonObject() throws JsonProcessingException, JSONException {
        JSONObject jsonObject = new JSONObject(objectMapper.writeValueAsString(this));
        return jsonObject;
    }

    @Override
    public String toJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }

    @Override
    public GrupoFarmaco fromJson(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, GrupoFarmaco.class);
    }

    @Override
    public GrupoFarmaco fromJsonObject(JSONObject response) throws IOException {
        return objectMapper.readValue(String.valueOf(response), GrupoFarmaco.class);
    }
}

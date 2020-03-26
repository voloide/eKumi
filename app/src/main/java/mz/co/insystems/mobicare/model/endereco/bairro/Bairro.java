package mz.co.insystems.mobicare.model.endereco.bairro;


import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.common.JsonParseble;
import mz.co.insystems.mobicare.common.LocalizacaoObject;
import mz.co.insystems.mobicare.model.endereco.municipio.Municipio;

/**
 * Created by voloide on 9/15/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = Bairro.TABLE_NAME_BAIRRO, daoClass = BairroDaoImpl.class)
public class Bairro extends BaseVO implements LocalizacaoObject, JsonParseble<Bairro> {

    public static final String TABLE_NAME_BAIRRO                    = "bairro";
    public static final String COLUMN_BAIRRO_ID 			        = "id";
    public static final String COLUMN_BAIRRO_DESIGNACAO 			= "designacao";
    public static final String COLUMN_BAIRRO_DESCRICAO              = "descricao";
    public static final String COLUMN_BAIRRO_ZONA                   = "zona";
    public static final String COLUMN_BAIRRO_MUNICIPIO              = "municipio_id";


    private static final long serialVersionUID = 1L;

    @DatabaseField(columnName = COLUMN_BAIRRO_ID, id = true, generatedId = false)
    private int id;
    @DatabaseField(columnName = COLUMN_BAIRRO_DESIGNACAO)
    private String designacao;
    @DatabaseField(columnName = COLUMN_BAIRRO_DESCRICAO)
    private String descricao;

    @DatabaseField(columnName = COLUMN_BAIRRO_MUNICIPIO, foreign = true, foreignAutoRefresh = true)
    private Municipio municipio;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Bairro(){}


    @Bindable
    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
        notifyPropertyChanged(BR.designacao);
    }
    @Bindable
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
        notifyPropertyChanged(BR.descricao);
    }

    public Bairro(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
        notifyPropertyChanged(BR.municipio);
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
    public Bairro fromJson(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, Bairro.class);
    }

    @Override
    public Bairro fromJsonObject(JSONObject response) throws IOException {
        return objectMapper.readValue(String.valueOf(response), Bairro.class);
    }
}

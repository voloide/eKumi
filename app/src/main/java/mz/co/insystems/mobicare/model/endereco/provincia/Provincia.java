package mz.co.insystems.mobicare.model.endereco.provincia;



import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

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

/**
 * Created by voloide on 9/15/16.
 */
@DatabaseTable(tableName = Provincia.TABLE_NAME_PROVINCIA, daoClass = ProvinciaDaoImpl.class)
public class Provincia extends BaseVO implements LocalizacaoObject, JsonParseble<Provincia> {

    public static final String TABLE_NAME_PROVINCIA                 = "provincia";
    public static final String COLUMN_PROVINCIA_ID 			        = "id";
    public static final String COLUMN_PROVINCIA_DESIGNACAO 			= "designacao";
    public static final String COLUMN_PROVINCIA_DESCRICAO           = "descricao";


    private static final long serialVersionUID = 1L;

    @DatabaseField(columnName = COLUMN_PROVINCIA_ID, id = true, generatedId = false)
    private int id;
    @DatabaseField(columnName = COLUMN_PROVINCIA_DESIGNACAO)
    private String designacao;
    @DatabaseField(columnName = COLUMN_PROVINCIA_DESCRICAO)
    private String descricao;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Provincia(){}

    public Provincia(int id) {
        this.id = id;
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
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
        notifyPropertyChanged(BR.descricao);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Override
    public String toString() {
        return "Provincia{" +
                "id=" + id +
                ", designacao='" + designacao + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
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
    public Provincia fromJson(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, Provincia.class);
    }

    @Override
    public Provincia fromJsonObject(JSONObject response) throws IOException {
        return objectMapper.readValue(String.valueOf(response), Provincia.class);
    }
}

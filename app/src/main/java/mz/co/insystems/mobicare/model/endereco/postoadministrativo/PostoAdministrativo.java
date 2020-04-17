package mz.co.insystems.mobicare.model.endereco.postoadministrativo;


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
import mz.co.insystems.mobicare.common.LocalizacaoObject;
import mz.co.insystems.mobicare.model.endereco.distrito.Distrito;

/**
 * Created by voloide on 9/15/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = PostoAdministrativo.TABLE_NAME_POSTO, daoClass = PostoAdministrativoDaoImpl.class)
public class PostoAdministrativo extends BaseVO implements LocalizacaoObject {

    public static final String TABLE_NAME_POSTO                         = "postoadministrativo";
    public static final String COLUMN_POSTO_ID 			                = "id";
    public static final String COLUMN_POSTO_DESIGNACAO 			        = "designacao";
    public static final String COLUMN_POSTO_DESCRICAO                   = "descricao";
    public static final String COLUMN_POSTO_DISTRITO_ID                 = "distrito_id";
    private static final long serialVersionUID = -7680745814852435017L;


    @DatabaseField(columnName = COLUMN_POSTO_ID, id = true, generatedId = false)
    private int id;
    @DatabaseField(columnName = COLUMN_POSTO_DESIGNACAO)
    private String designacao;
    @DatabaseField(columnName = COLUMN_POSTO_DESCRICAO)
    private String descricao;
    @DatabaseField(columnName = COLUMN_POSTO_DISTRITO_ID, foreign = true, foreignAutoRefresh = true)
    private Distrito distrito;

    public PostoAdministrativo(){}

    public PostoAdministrativo(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
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

    public void setId(int id) {

        this.id = id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
        notifyPropertyChanged(BR.distrito);
    }

}

package mz.co.insystems.service.ekumi.model.endereco.bairro;


import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.co.insystems.service.ekumi.base.AbstractBaseVO;
import mz.co.insystems.service.ekumi.common.LocalizacaoObject;
import mz.co.insystems.service.ekumi.model.endereco.municipio.Municipio;

/**
 * Created by voloide on 9/15/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = Bairro.TABLE_NAME_BAIRRO, daoClass = BairroDaoImpl.class)
public class Bairro extends AbstractBaseVO implements LocalizacaoObject {

    public static final String TABLE_NAME_BAIRRO                    = "bairro";
    public static final String COLUMN_BAIRRO_ID 			        = "id";
    public static final String COLUMN_BAIRRO_DESIGNACAO 			= "designacao";
    public static final String COLUMN_BAIRRO_DESCRICAO              = "descricao";
    public static final String COLUMN_BAIRRO_ZONA                   = "zona";
    public static final String COLUMN_BAIRRO_MUNICIPIO              = "municipio_id";
    private static final long serialVersionUID = 6135708212768840895L;


    @DatabaseField(columnName = COLUMN_BAIRRO_ID, id = true, generatedId = false)
    private int id;
    @DatabaseField(columnName = COLUMN_BAIRRO_DESIGNACAO)
    private String designacao;
    @DatabaseField(columnName = COLUMN_BAIRRO_DESCRICAO)
    private String descricao;

    @DatabaseField(columnName = COLUMN_BAIRRO_MUNICIPIO, foreign = true, foreignAutoRefresh = true)
    private Municipio municipio;

    public Bairro(){}

    public Bairro(String designacao){
        this.descricao = designacao;
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
}

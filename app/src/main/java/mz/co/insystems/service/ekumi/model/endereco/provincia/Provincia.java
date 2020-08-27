package mz.co.insystems.service.ekumi.model.endereco.provincia;



import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.co.insystems.service.ekumi.base.AbstractBaseVO;
import mz.co.insystems.service.ekumi.common.LocalizacaoObject;

/**
 * Created by voloide on 9/15/16.
 */
@DatabaseTable(tableName = Provincia.TABLE_NAME_PROVINCIA, daoClass = ProvinciaDaoImpl.class)
public class Provincia extends AbstractBaseVO implements LocalizacaoObject {

    public static final String TABLE_NAME_PROVINCIA                 = "provincia";
    public static final String COLUMN_PROVINCIA_ID 			        = "id";
    public static final String COLUMN_PROVINCIA_DESIGNACAO 			= "designacao";
    public static final String COLUMN_PROVINCIA_DESCRICAO           = "descricao";
    private static final long serialVersionUID = 5171724973120764209L;


    @DatabaseField(columnName = COLUMN_PROVINCIA_ID, id = true, generatedId = false)
    private int id;
    @DatabaseField(columnName = COLUMN_PROVINCIA_DESIGNACAO)
    private String designacao;
    @DatabaseField(columnName = COLUMN_PROVINCIA_DESCRICAO)
    private String descricao;

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
}

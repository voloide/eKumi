package mz.co.insystems.service.ekumi.model.servico;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.co.insystems.service.ekumi.base.AbstractBaseVO;
import mz.co.insystems.service.ekumi.model.farmacia.Farmacia;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = Servico.TABLE_NAME_SERVICO, daoClass = ServicoDaoImpl.class)
public class Servico extends AbstractBaseVO {
    public static final String TABLE_NAME_SERVICO			                        = "servico";
    public static final String COLUMN_SERVICO_ID 			                        = "id";
    public static final String COLUMN_SERVICO_DESIGNACAO			                = "designacao";
    public static final String COLUMN_SERVICO_DESCRICAO			                    = "descricao";
    public static final String COLUMN_SERVICO_ESTADO			                    = "estado";
    public static final String COLUMN_FARMACIA_ID			                        = "farmacia_id";
    private static final long serialVersionUID = 656905629072588521L;


    @DatabaseField(columnName = COLUMN_SERVICO_ID, id = true, generatedId = false)
    private long id;
    @DatabaseField(columnName = COLUMN_SERVICO_DESIGNACAO)
    private String designacao;
    @DatabaseField(columnName = COLUMN_SERVICO_DESCRICAO)
    private String descricao;
    @DatabaseField
    private int estado;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Servico.COLUMN_FARMACIA_ID)
    private Farmacia farmacia;

    @DatabaseField()
    private String logo;

    public Servico() {
    }

    public long getId() {
        return id;
    }

    public Farmacia getFarmacia() {
        return this.farmacia;
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
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
        notifyPropertyChanged(BR.descricao);
    }
    @Bindable
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
        notifyPropertyChanged(BR.estado);
    }


    public void setLogo(String logo) {
        this.logo = logo;
        notifyPropertyChanged(BR.logo);
    }

    @Bindable
    public String getLogo() {
        return logo;
    }
}

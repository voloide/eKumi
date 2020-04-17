package mz.co.insystems.mobicare.model.farmaciafarmaco;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.model.farmacia.Farmacia;
import mz.co.insystems.mobicare.model.farmaco.Farmaco;


@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = FarmacoFarmacia.TABLE_NAME_FARMACIA_FARMACO, daoClass = FarmacoFarmaciaDaoImpl.class)
public class FarmacoFarmacia extends BaseVO {
    private static final long serialVersionUID = 2283800903900808822L;

    public static final String TABLE_NAME_FARMACIA_FARMACO			= "farmacia_farmaco";
    public static final String COLUMN_FARMACIA_FARMACO_ID 			= "id";
    public static final String COLUMN_FARMACO_ID			        = "farmaco_id";
    public static final String COLUMN_FARMACIA_ID			        = "farmacia_id";
    public static final String COLUMN_FARMACO_DISPONIBILIDADE       = "disponibilidade";
    public static final String COLUMN_FARMACO_PRECO 	            = "preco";

    @DatabaseField
    private long id;

    @DatabaseField(columnName = FarmacoFarmacia.COLUMN_FARMACIA_ID, foreign = true)
    private Farmacia farmacia;

    @DatabaseField(columnName = FarmacoFarmacia.COLUMN_FARMACO_ID, foreign = true)
    private Farmaco farmaco;

    @DatabaseField
    private int disponibilidade;

    @DatabaseField
    private double preco;

    public FarmacoFarmacia() {
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
    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
        notifyPropertyChanged(BR.farmacia);
    }

    @Bindable
    public Farmaco getFarmaco() {
        return farmaco;
    }

    public void setFarmaco(Farmaco farmaco) {
        this.farmaco = farmaco;
        notifyPropertyChanged(BR.farmaco);
    }

    @Bindable
    public int getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(int disponibilidade) {
        this.disponibilidade = disponibilidade;
        notifyPropertyChanged(BR.disponibilidade);
    }

    @Bindable
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
        notifyPropertyChanged(BR.preco);
    }
}

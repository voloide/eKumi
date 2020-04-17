package mz.co.insystems.mobicare.model.farmaco;

import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.model.contacto.Contacto;
import mz.co.insystems.mobicare.model.endereco.Endereco;
import mz.co.insystems.mobicare.model.farmacia.Farmacia;
import mz.co.insystems.mobicare.model.farmaciafarmaco.FarmacoFarmacia;
import mz.co.insystems.mobicare.model.grupofarmaco.GrupoFarmaco;
import mz.co.insystems.mobicare.model.search.Searchble;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = Farmaco.TABLE_NAME_FARMACO, daoClass = FarmacoDaoImpl.class)
public class Farmaco extends BaseVO implements Searchble {
    public static final String TABLE_NAME_FARMACO			        = "farmaco";
    public static final String COLUMN_FARMACO_ID 			        = "id";
    public static final String COLUMN_FARMACO_DESIGNACAO 			= "designacao";
    public static final String COLUMN_FARMACO_DISPONIBILIDADE       = "disponibilidade";
    public static final String COLUMN_FARMACO_PRECO 	            = "preco";
    public static final String COLUMN_FARMACO_GRUPO 	            = "grupofarmaco_id";
    public static final String COLUMN_FARMACIA_ID 	                = "farmacia_id";


    public static final int FARMACO_DISPONIVEL 	=1;
    public static final int FARMACO_INDISPONIVEL 	= 0;
    private static final long serialVersionUID = -1261080191252499707L;


    @DatabaseField
    private long id;
    @DatabaseField
    private String designacao;

    @DatabaseField
    private String logo;

    @JsonProperty(GrupoFarmaco.TABLE_NAME_FARMACO)
    @DatabaseField(columnName = COLUMN_FARMACO_GRUPO, foreign = true, foreignAutoRefresh = true)
    private GrupoFarmaco grupoFarmaco;

    @JsonProperty("farmacia")
    private Farmacia farmacia;

    @JsonProperty("farmacia_farmaco")
    private FarmacoFarmacia farmacoInfo;

    public Farmaco(){}

    public Farmaco(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }


    public int getDisponibilidade() {
        return this.farmacoInfo.getDisponibilidade();
    }

    @Override
    public double getPreco() {
        return this.farmacoInfo.getPreco();
    }

    @Override
    public String getFarmaciaName() {
        return this.farmacia.getDescricao();
    }

    @Override
    public String getDistancia() {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);
        return df.format(this.farmacia.getDistance())+"Km";
    }

    @Override
    public String getImage() {
        return this.logo;
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
    public GrupoFarmaco getGrupoFarmaco() {
        return grupoFarmaco;
    }

    public void setGrupoFarmaco(GrupoFarmaco grupoFarmaco) {
        this.grupoFarmaco = grupoFarmaco;
        notifyPropertyChanged(BR.grupoFarmaco);
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
    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
        notifyPropertyChanged(BR.farmacia);
    }

    @Bindable
    public FarmacoFarmacia getFarmacoInfo() {
        return farmacoInfo;
    }

    public void setFarmacoInfo(FarmacoFarmacia farmacoInfo) {
        this.farmacoInfo = farmacoInfo;
        notifyPropertyChanged(BR.farmacoInfo);
    }

    @BindingAdapter({"logo"})
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .circleCrop())
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
}

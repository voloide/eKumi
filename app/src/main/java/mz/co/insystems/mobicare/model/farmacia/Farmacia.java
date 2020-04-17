package mz.co.insystems.mobicare.model.farmacia;

import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.model.contacto.Contacto;
import mz.co.insystems.mobicare.model.endereco.Endereco;
import mz.co.insystems.mobicare.model.farmaco.Farmaco;
import mz.co.insystems.mobicare.model.search.Searchble;
import mz.co.insystems.mobicare.model.servico.Servico;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = Farmacia.TABLE_NAME_FARMACIA, daoClass = FarmaciaDaoImpl.class)
public class Farmacia extends BaseVO {
    public static final String TABLE_NAME_FARMACIA			                = "farmacia";
    public static final String COLUMN_FARMACIA_ID 			                = "id";
    public static final String COLUMN_FARMACIA_NOME			                = "nome";
    public static final String COLUMN_FARMACIA_ENDERECO 			        = "endereco_id";
    public static final String COLUMN_FARMACIA_CONTACTO 			        = "contacto_id";
    public static final String COLUMN_FARMACIA_ESTADO 			            = "estado";
    public static final String COLUMN_FARMACIA_SEMPRE_ABERTO 			    = "sempre_aberto";
    public static final String COLUMN_FARMACIA_HORA_INICIO 			        = "hora_inicio";
    public static final String COLUMN_FARMACIA_HORA_FIM 			        = "hora_fim";
    public static final String COLUMN_FARMACIA_NUIT 			            = "nuit";
    public static final String COLUMN_FARMACIA_DATA_REGISTO 			    = "data_registo";
    private static final long serialVersionUID = 2751774452489833246L;


    @DatabaseField(columnName = COLUMN_FARMACIA_ID, id = true, generatedId = false)
    private int id;
    @DatabaseField
    private String nome;
    @DatabaseField
    private int estado;
    @DatabaseField(columnName = COLUMN_FARMACIA_ENDERECO, foreign = true, foreignAutoRefresh = true)
    private Endereco endereco;

    @DatabaseField(columnName = COLUMN_FARMACIA_CONTACTO, foreign = true, foreignAutoRefresh = true)
    private Contacto contacto;
    @ForeignCollectionField
    private Collection<Servico> servicos;

    @DatabaseField()
    private String logo;


    private List<Farmaco> farmacos;

    private double distance;


    @JsonProperty(COLUMN_FARMACIA_SEMPRE_ABERTO)
    @DatabaseField(columnName = COLUMN_FARMACIA_SEMPRE_ABERTO)
    private int sempreAberto;

    @JsonFormat(pattern = "HH:mm:ss")
    @JsonProperty(COLUMN_FARMACIA_HORA_INICIO)
    @DatabaseField(columnName = COLUMN_FARMACIA_HORA_INICIO)
    private Date horaInicio;

    @JsonFormat(pattern = "HH:mm:ss")
    @JsonProperty(COLUMN_FARMACIA_HORA_FIM)
    @DatabaseField(columnName = COLUMN_FARMACIA_HORA_FIM)
    private Date horaFim;

    @JsonProperty(COLUMN_FARMACIA_NUIT)
    @DatabaseField(columnName = COLUMN_FARMACIA_NUIT)
    private long nuit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(COLUMN_FARMACIA_DATA_REGISTO)
    @DatabaseField(columnName = COLUMN_FARMACIA_DATA_REGISTO)
    private Date dataRegisto;



    public Farmacia(int id, String nome, int estado, Endereco endereco, Contacto contacto) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
        this.endereco = endereco;
        this.contacto = contacto;
    }
    public Farmacia() {

    }

    @Override
    public String toString() {
        return "GrupoFarmaco{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", estado=" + estado +
                ", endereco=" + endereco +
                ", contacto=" + contacto +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        notifyPropertyChanged(BR.nome);
    }
    @Bindable
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
        notifyPropertyChanged(BR.estado);
    }
    @Bindable
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        notifyPropertyChanged(BR.endereco);
    }
    @Bindable
    public Contacto getContacto() {
        return contacto;

    }

    public Farmacia getFarmacia() {
        throw new RuntimeException("Metodo nao aplicavel");
    }


    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
        notifyPropertyChanged(BR.contacto);
    }

    public Collection<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(Collection<Servico> servicos) {
        this.servicos = servicos;
    }

    @Bindable
    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
        notifyPropertyChanged(BR.logo);
    }

    public String getDescricao() {
        return this.nome;
    }

    public List<Farmaco> getFarmacos() {
        return farmacos;
    }

    public void setFarmacos(List<Farmaco> farmacos) {
        this.farmacos = farmacos;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Bindable
    public int getSempreAberto() {
        return sempreAberto;
    }

    public void setSempreAberto(int sempreAberto) {
        this.sempreAberto = sempreAberto;
        notifyPropertyChanged(BR.sempreAberto);
    }

    @Bindable
    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
        notifyPropertyChanged(BR.horaInicio);
    }

    @Bindable
    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
        notifyPropertyChanged(BR.horaFim);
    }

    @Bindable
    public long getNuit() {
        return nuit;
    }

    public void setNuit(long nuit) {
        this.nuit = nuit;
        notifyPropertyChanged(BR.nuit);
    }

    @Bindable
    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
        notifyPropertyChanged(BR.dataRegisto);
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

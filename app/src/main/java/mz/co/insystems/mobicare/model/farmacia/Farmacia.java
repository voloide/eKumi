package mz.co.insystems.mobicare.model.farmacia;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;

import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.common.JsonParseble;
import mz.co.insystems.mobicare.model.contacto.Contacto;
import mz.co.insystems.mobicare.model.endereco.Endereco;
import mz.co.insystems.mobicare.model.search.Searchble;
import mz.co.insystems.mobicare.model.servico.Servico;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = Farmacia.TABLE_NAME_FARMACIA, daoClass = FarmaciaDaoImpl.class)
public class Farmacia extends BaseVO implements JsonParseble<Farmacia>, Searchble {
    public static final String TABLE_NAME_FARMACIA			                = "farmacia";
    public static final String COLUMN_FARMACIA_ID 			                = "id";
    public static final String COLUMN_FARMACIA_NOME			                = "nome";
    public static final String COLUMN_FARMACIA_ENDERECO 			        = "endereco_id";
    public static final String COLUMN_FARMACIA_CONTACTO 			        = "contacto_id";
    public static final String COLUMN_FARMACIA_ESTADO 			            = "estado";


    private static final long serialVersionUID = 1L;

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

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] logo;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] image;

    private ObjectMapper objectMapper = new ObjectMapper();

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

    public int getDisponibilidade() {
        return this.estado;
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
    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
        notifyPropertyChanged(BR.logo);
    }

    @Bindable
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
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
    public Farmacia fromJson(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, Farmacia.class);
    }

    @Override
    public Farmacia fromJsonObject(JSONObject response) throws IOException {
        return objectMapper.readValue(String.valueOf(response), Farmacia.class);
    }

    public String getDescricao() {
        return this.nome;
    }
}

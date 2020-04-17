package mz.co.insystems.mobicare.model.pessoa;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.model.contacto.Contacto;
import mz.co.insystems.mobicare.model.endereco.Endereco;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = Pessoa.TABLE_NAME, daoClass = PessoaDaoImpl.class)
public class Pessoa extends BaseVO {
    public static final String TABLE_NAME 	        = "pessoa";
    public static final String COLUMN_ID 	        = "id";
    public static final String COLUMN_NAME 	        = "nome";
    public static final String COLUMN_SURNAME       = "apelido";
    public static final String COLUMN_CONTACT_ID 	= "contacto_id";
    public static final String COLUMN_ENDERECO_ID 	= "endereco_id";
    private static final long serialVersionUID = 5789756246180641218L;


    @DatabaseField(columnName = COLUMN_ID, id = true)
    private int id;

    @JsonProperty(Pessoa.COLUMN_NAME)
    @DatabaseField(columnName = COLUMN_NAME)
    private String name;

    @JsonProperty(Pessoa.COLUMN_SURNAME)
    @DatabaseField(columnName = COLUMN_SURNAME)
    private String surname;
    @DatabaseField(columnName = COLUMN_CONTACT_ID, foreign = true, foreignAutoRefresh = true)
    private Contacto contacto;
    @DatabaseField(columnName = COLUMN_ENDERECO_ID, foreign = true, foreignAutoRefresh = true)
    private Endereco endereco;


    public Pessoa(int id, String name, String surname, Contacto contacto, Endereco endereco) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.contacto = contacto;
        this.endereco = endereco;
    }

    @Bindable
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        notifyPropertyChanged(BR.endereco);
    }

    public Pessoa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        notifyPropertyChanged(BR.surname);
    }

    @Bindable
    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
        notifyPropertyChanged(BR.contacto);
    }
}

package mz.co.insystems.service.ekumi.model.contacto;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.co.insystems.service.ekumi.base.AbstractBaseVO;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = Contacto.TABLE_NAME_CONTACT, daoClass = ContactDaoImpl.class)
public class Contacto extends AbstractBaseVO {
    public static final String TABLE_NAME_CONTACT			    = "contacto";
    public static final String COLUMN_CONTACT_ID 			    = "id";
    public static final String COLUMN_CONTACT_EMAIL 			= "email";
    public static final String COLUMN_CONTACT_PHONE_NUMBER_MAIN = "telefone01";
    public static final String COLUMN_CONTACT_PHONE_NUMBER_AUX 	= "telefone02";
    private static final long serialVersionUID = 264724605087921392L;


    @DatabaseField(columnName = COLUMN_CONTACT_ID, id = true, generatedId = false)
    private int id;
    @DatabaseField
    private String email;
    @DatabaseField
    private String telefone01;
    @DatabaseField
    private String telefone02;


    public Contacto(int id, String email, String mainMobileNumber, String auxMobileNumber) {
        this.id = id;
        this.email = email;
        this.telefone01 = mainMobileNumber;
        this.telefone02 = auxMobileNumber;
    }

    public Contacto(){
    }



    public Contacto(int id) {
        this.id = id;
    }



    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getTelefone01() {
        return telefone01;
    }

    public void setTelefone01(String mainMobileNumber) {
        this.telefone01 = mainMobileNumber;
        notifyPropertyChanged(BR.telefone01);
    }

    @Bindable
    public String getTelefone02() {
        return telefone02;
    }

    public void setTelefone02(String auxMobileNumber) {
        this.telefone02 = auxMobileNumber;
        notifyPropertyChanged(BR.telefone02);
    }
}

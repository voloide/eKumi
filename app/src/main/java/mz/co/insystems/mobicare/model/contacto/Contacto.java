package mz.co.insystems.mobicare.model.contacto;

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
import mz.co.insystems.mobicare.common.JsonParseble;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = Contacto.TABLE_NAME_CONTACT, daoClass = ContactDaoImpl.class)
public class Contacto extends BaseVO implements JsonParseble<Contacto> {
    public static final String TABLE_NAME_CONTACT			    = "contacto";
    public static final String COLUMN_CONTACT_ID 			    = "id";
    public static final String COLUMN_CONTACT_EMAIL 			= "email";
    public static final String COLUMN_CONTACT_PHONE_NUMBER_MAIN = "mainMobileNumber";
    public static final String COLUMN_CONTACT_PHONE_NUMBER_AUX 	= "auxMobileNumber";

    private static final long serialVersionUID = 1L;

    @DatabaseField(columnName = COLUMN_CONTACT_ID, id = true, generatedId = false)
    private int id;
    @DatabaseField
    private String email;
    @DatabaseField
    private String mainMobileNumber;
    @DatabaseField
    private String auxMobileNumber;

    private ObjectMapper objectMapper;

    public Contacto(int id, String email, String mainMobileNumber, String auxMobileNumber) {
        this.id = id;
        this.email = email;
        this.mainMobileNumber = mainMobileNumber;
        this.auxMobileNumber = auxMobileNumber;
        this.objectMapper = new ObjectMapper();
    }

    public Contacto(){
        this.objectMapper = new ObjectMapper();
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
    public String getMainMobileNumber() {
        return mainMobileNumber;
    }

    public void setMainMobileNumber(String mainMobileNumber) {
        this.mainMobileNumber = mainMobileNumber;
        notifyPropertyChanged(BR.mainMobileNumber);
    }

    @Bindable
    public String getAuxMobileNumber() {
        return auxMobileNumber;
    }

    public void setAuxMobileNumber(String auxMobileNumber) {
        this.auxMobileNumber = auxMobileNumber;
        notifyPropertyChanged(BR.auxMobileNumber);
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
    public Contacto fromJson(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, Contacto.class);
    }

    @Override
    public Contacto fromJsonObject(JSONObject response) throws IOException {
        return objectMapper.readValue(String.valueOf(response), Contacto.class);
    }
}

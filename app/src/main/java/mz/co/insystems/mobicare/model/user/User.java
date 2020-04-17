package mz.co.insystems.mobicare.model.user;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.model.farmacia.Farmacia;
import mz.co.insystems.mobicare.model.pessoa.Pessoa;
import mz.co.insystems.mobicare.util.Utilities;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = User.TABLE_NAME, daoClass = UserDaoImpl.class)
public class User extends BaseVO {

    public static final String TABLE_NAME           = "user";
    public static final String COLUMN_ID 			= "id";
    public static final String COLUMN_USER_NAME		= "user_name";
    public static final String COLUMN_PASSWORD 		= "password";
    public static final String COLUMN_ESTADO 		= "estado";
    public static final String COLUMN_PESSOA_ID		= "pessoa_id";
    public static final String COLUMN_FARMACIA_ID 	= "farmacia_id";

    private static final long serialVersionUID = 8371723958393408427L;

    @DatabaseField(columnName = COLUMN_ID, id = true, generatedId = false)
    private int id;

    @JsonProperty(User.COLUMN_USER_NAME)
    @DatabaseField(columnName = COLUMN_USER_NAME, dataType = DataType.STRING)
    private String userName;

    @DatabaseField
    private String password;
    @DatabaseField(columnName = COLUMN_ESTADO)
    private int estado;

    @DatabaseField(columnName = COLUMN_PESSOA_ID, foreign = true, foreignAutoRefresh = true)
    private Pessoa pessoa;

    @DatabaseField(columnName = COLUMN_FARMACIA_ID, foreign = true, foreignAutoRefresh = true)
    private Farmacia farmacia;

    private String passwordConfirm;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = Utilities.MD5Crypt(password);
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public boolean isPasswordConfirmed(){
        return this.password.equals(this.passwordConfirm);
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }



    public boolean isActive(){
        return this.estado == 1;
    }


    @Bindable
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
        notifyPropertyChanged(BR.estado);
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);

    }

    @Bindable
    public String getPassword() {
        return password;
    }

    @Bindable
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        notifyPropertyChanged(BR.pessoa);
    }
    @Bindable
    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
        notifyPropertyChanged(BR.farmacia);
    }

    public boolean isFarmacia() {
        return this.farmacia != null && this.pessoa == null;
    }

}

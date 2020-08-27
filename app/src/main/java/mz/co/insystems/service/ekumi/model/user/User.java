package mz.co.insystems.service.ekumi.model.user;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.co.insystems.service.ekumi.base.AbstractBaseVO;
import mz.co.insystems.service.ekumi.model.farmacia.Farmacia;
import mz.co.insystems.service.ekumi.model.pessoa.Pessoa;
import mz.co.insystems.service.ekumi.util.Utilities;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = User.TABLE_NAME, daoClass = UserDaoImpl.class)
public class User extends AbstractBaseVO {

    public static final String TABLE_NAME           = "user";
    public static final String COLUMN_ID 			= "id";
    public static final String COLUMN_USER_NAME		= "user_name";
    public static final String COLUMN_MOBILE_NUMBER	= "mobile_number";
    public static final String COLUMN_USER_PIN		= "pin";
    public static final String COLUMN_PASSWORD 		= "password";
    public static final String COLUMN_ESTADO 		= "estado";
    public static final String COLUMN_PESSOA_ID		= "pessoa_id";
    public static final String COLUMN_FARMACIA_ID 	= "farmacia_id";

    private static final long serialVersionUID = 8371723958393408427L;

    private String pin_digit_01;
    private String pin_digit_02;
    private String pin_digit_03;
    private String pin_digit_04;

    @DatabaseField(columnName = COLUMN_ID, id = true, generatedId = false)
    private int id;

    @JsonProperty(User.COLUMN_USER_NAME)
    @DatabaseField(columnName = COLUMN_USER_NAME, dataType = DataType.STRING)
    private String userName;

    @DatabaseField
    private String password;

    @DatabaseField
    private String pin;

    @DatabaseField
    private String mobileNmber;

    @DatabaseField
    private String email;

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

    @Bindable
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
        notifyPropertyChanged(BR.pin);
    }

    @Bindable
    public String getPin_digit_01() {
        return pin_digit_01;
    }

    public void setPin_digit_01(String pin_digit_01) {
        this.pin_digit_01 = pin_digit_01;
        notifyPropertyChanged(BR.pin_digit_01);
    }

    @Bindable
    public String getPin_digit_02() {
        return pin_digit_02;
    }

    public void setPin_digit_02(String pin_digit_02) {
        this.pin_digit_02 = pin_digit_02;
        notifyPropertyChanged(BR.pin_digit_02);
    }

    @Bindable
    public String getPin_digit_03() {
        return pin_digit_03;
    }

    public void setPin_digit_03(String pin_digit_03) {
        this.pin_digit_03 = pin_digit_03;
        notifyPropertyChanged(BR.pin_digit_03);
    }

    @Bindable
    public String getPin_digit_04() {
        return pin_digit_04;
    }

    public void setPin_digit_04(String pin_digit_04) {
        this.pin_digit_04 = pin_digit_04;
        notifyPropertyChanged(BR.pin_digit_04);
    }

    @Bindable
    public String getMobileNmber() {
        return mobileNmber;
    }

    public void setMobileNmber(String mobileNmber) {
        this.mobileNmber = mobileNmber;
        notifyPropertyChanged(BR.mobileNmber);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public boolean isFarmacia() {
        return this.farmacia != null && this.pessoa == null;
    }

}

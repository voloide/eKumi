package mz.co.insystems.service.ekumi.model.user.service;

import android.content.Context;

import java.sql.SQLException;

import mz.co.insystems.service.ekumi.base.AbstractBaseService;
import mz.co.insystems.service.ekumi.base.SearchResult;
import mz.co.insystems.service.ekumi.model.user.User;
import mz.co.insystems.service.ekumi.model.user.UserDao;
import mz.co.insystems.service.ekumi.util.Utilities;

public class UserService extends AbstractBaseService {

    private UserDao mUserDao;

    private User currUser;
    private static UserService instance;
    private String webRequestResult;




    private UserService(Context context) {
        super(context);
        try {
            mUserDao = getDbHelper().getUserDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        currUser = null;

        this.webService = UserWebService.getInstance(context);


    }

    public static UserService getInstance(Context context){
        if (instance == null){
            instance = new UserService(context);
        }

        return instance;
    }

    public User getUserById(User user) throws SQLException {
        return mUserDao.getById(user.getId());
    }

    public User saveUser(User user) throws SQLException {
        return mUserDao.createIfNotExists(user);
    }

    public boolean isAuthentic(User user) throws SQLException {
        return mUserDao.isAuthentic(user);
    }

    public User getByCredencials(User user) throws SQLException {
        return mUserDao.getByCredencials(user);
    }

    public void deleteAll() throws SQLException {
        mUserDao.deleteAllUser();
    }

    public User getByPin(int pin) throws SQLException {
        return mUserDao.getByPin(pin);
    }

    public boolean isValidPin(int pin) throws SQLException {
        return mUserDao.isValidPin(pin);
    }

    public User getUserFromWebByCredentials(User user) throws SQLException {
        SearchResult<User> searchResult = makeDataRequest(user, UserWebService.UPDATE_LOGIN_STATUS);
        if (Utilities.listHasElements(searchResult.getCurrentSearchedRecords())){
            return searchResult.getCurrentSearchedRecords().get(0);
        }

        return null;
    }

    public User tryToUpdateLoginStatusOnWeb(User user){
        SearchResult<User> searchResult = makeDataRequest(user, UserWebService.UPDATE_LOGIN_STATUS);
        if (Utilities.listHasElements(searchResult.getCurrentSearchedRecords())){
            return searchResult.getCurrentSearchedRecords().get(0);
        }

        return null;
    }

    public boolean areUsersOnDB() throws SQLException {
        return Utilities.listHasElements(mUserDao.queryForAll());
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public boolean isMobileNumberInUse(User user) {
        return isResourceAvailable(user, UserWebService.SERVICE_CHECK_MOBILE_NUMBER_AVAILABILITY);
    }

    public User resetPin(User user){
       SearchResult<User> searchResult = makeDataRequest(user, UserWebService.SERVICE_CHECK_MOBILE_NUMBER_AVAILABILITY);
       if (Utilities.listHasElements(searchResult.getCurrentSearchedRecords())){
           return searchResult.getCurrentSearchedRecords().get(0);
       }

       return null;
    }

    public String getWebRequestResult() {
        return webRequestResult;
    }

    public void setWebRequestResult(String webRequestResult) {
        this.webRequestResult = webRequestResult;
    }

    @Override
    public UserWebService getWebService() {
        return (UserWebService) super.getWebService();
    }

    public void sendConfirmationCode() {
    }

    public void confirUserCode() {
    }
}

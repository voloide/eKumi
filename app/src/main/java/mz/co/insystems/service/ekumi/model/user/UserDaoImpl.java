package mz.co.insystems.service.ekumi.model.user;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.io.Serializable;
import java.sql.SQLException;

import mz.co.insystems.service.ekumi.base.AbstractBaseDAO;
import mz.co.insystems.service.ekumi.util.Utilities;

/**
 * Created by Voloide Tamele on 10/2/2017.
 */
public class UserDaoImpl extends AbstractBaseDAO<User, Integer> implements UserDao, Serializable {


    public UserDaoImpl(Class<User> dataClass) throws SQLException {
        super(dataClass);
    }

    public UserDaoImpl(ConnectionSource connectionSource)throws SQLException {
        super(connectionSource, User.class);
    }

    public UserDaoImpl(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public UserDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<User> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public boolean isAuthentic(User user) throws SQLException {
        return this.queryBuilder().where()
                .eq(User.COLUMN_USER_NAME, user.getUserName())
                .and()
                .eq(User.COLUMN_PASSWORD, Utilities.MD5Crypt(user.getPassword())).queryForFirst() != null;
    }
    @Override
    public User getByCredencials(User user) throws SQLException {
        return this.queryBuilder().where()
                .eq(User.COLUMN_USER_NAME, user.getUserName())
                .and()
                .eq(User.COLUMN_PASSWORD, Utilities.MD5Crypt(user.getPassword())).queryForFirst();
    }

    @Override
    public User getById(int id) throws SQLException {
        return this.queryForId(id);
    }

    @Override
    public User getByPin(int pin) throws SQLException {
        return this.queryForEq(User.COLUMN_USER_PIN, pin).get(0);
    }

    @Override
    public boolean isValidPin(int pin) throws SQLException {
        return utilities.listHasElements(this.queryForEq(User.COLUMN_USER_PIN, pin));
    }

    @Override
    public void deleteAllUser() throws SQLException {
        deleteBuilder().delete();
    }
}

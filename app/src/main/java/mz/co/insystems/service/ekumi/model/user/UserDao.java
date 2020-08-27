package mz.co.insystems.service.ekumi.model.user;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by Voloide Tamele on 11/6/2017.
 */

public interface UserDao extends Dao<User, Integer>{

    boolean isAuthentic(User user) throws SQLException;

    User getByCredencials(User user) throws SQLException;

    User getById(int id) throws SQLException;

    User getByPin(int pin) throws SQLException;

    boolean isValidPin(int pin) throws SQLException;

    void deleteAllUser() throws SQLException;
}

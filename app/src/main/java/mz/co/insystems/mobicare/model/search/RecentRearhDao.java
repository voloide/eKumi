package mz.co.insystems.mobicare.model.search;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import mz.co.insystems.mobicare.model.user.User;

/**
 * Created by Voloide Tamele on 5/10/2018.
 */

public interface RecentRearhDao extends Dao<RecentSearch, Integer> {
    List<RecentSearch> getAllOfUser(User user) throws SQLException;
}

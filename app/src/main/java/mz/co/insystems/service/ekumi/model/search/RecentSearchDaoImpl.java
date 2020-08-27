package mz.co.insystems.service.ekumi.model.search;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.co.insystems.service.ekumi.base.AbstractBaseDAO;
import mz.co.insystems.service.ekumi.model.user.User;

/**
 * Created by Voloide Tamele on 5/10/2018.
 */

public class RecentSearchDaoImpl extends AbstractBaseDAO<RecentSearch, Integer> implements RecentRearhDao {


    public RecentSearchDaoImpl(Class<RecentSearch> dataClass) throws SQLException {
        super(dataClass);
    }

    public RecentSearchDaoImpl(ConnectionSource connectionSource, Class<RecentSearch> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public RecentSearchDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<RecentSearch> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<RecentSearch> getAllOfUser(User user) throws SQLException {
        return (List<RecentSearch>) queryBuilder().where().eq(RecentSearch.COLUMN_USER, user.getId());
    }
}

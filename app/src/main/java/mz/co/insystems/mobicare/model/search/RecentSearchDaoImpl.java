package mz.co.insystems.mobicare.model.search;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.co.insystems.mobicare.model.user.User;

/**
 * Created by Voloide Tamele on 5/10/2018.
 */

public class RecentSearchDaoImpl extends BaseDaoImpl<RecentSearch, Integer> implements RecentRearhDao {
    protected RecentSearchDaoImpl(Class<RecentSearch> dataClass) throws SQLException {
        super(dataClass);
    }

    protected RecentSearchDaoImpl(ConnectionSource connectionSource, Class<RecentSearch> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected RecentSearchDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<RecentSearch> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<RecentSearch> getAllOfUser(User user) throws SQLException {
        return (List<RecentSearch>) queryBuilder().where().eq(RecentSearch.COLUMN_USER, user.getId());
    }
}

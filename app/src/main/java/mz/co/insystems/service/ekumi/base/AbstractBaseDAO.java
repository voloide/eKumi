package mz.co.insystems.service.ekumi.base;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.co.insystems.service.ekumi.util.Utilities;

public abstract class AbstractBaseDAO<T, ID> extends BaseDaoImpl<T, ID> {
    protected Utilities utilities = Utilities.getInstance();

    protected AbstractBaseDAO(Class<T> dataClass) throws SQLException {
        super(dataClass);
    }

    protected AbstractBaseDAO(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected AbstractBaseDAO(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

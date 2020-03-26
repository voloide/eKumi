package mz.co.insystems.mobicare.model.contacto;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

public class ContactDaoImpl extends BaseDaoImpl<Contacto, Integer> implements ContactDao{
    protected ContactDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    protected ContactDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected ContactDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

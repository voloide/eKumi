package mz.co.insystems.service.ekumi.model.contacto;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.co.insystems.service.ekumi.base.AbstractBaseDAO;

public class ContactDaoImpl extends AbstractBaseDAO<Contacto, Integer> implements ContactDao{

    public ContactDaoImpl(Class<Contacto> dataClass) throws SQLException {
        super(dataClass);
    }

    public ContactDaoImpl(ConnectionSource connectionSource, Class<Contacto> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ContactDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Contacto> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

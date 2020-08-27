package mz.co.insystems.service.ekumi.model.endereco.provincia;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.co.insystems.service.ekumi.base.AbstractBaseDAO;

/**
 * Created by Voloide Tamele on 10/3/2017.
 */
public class ProvinciaDaoImpl extends AbstractBaseDAO<Provincia, Integer> implements ProvinciaDao {

    public ProvinciaDaoImpl(Class<Provincia> dataClass) throws SQLException {
        super(dataClass);
    }

    public ProvinciaDaoImpl(ConnectionSource connectionSource, Class<Provincia> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ProvinciaDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Provincia> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

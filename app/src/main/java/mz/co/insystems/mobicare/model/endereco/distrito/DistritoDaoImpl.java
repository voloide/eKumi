package mz.co.insystems.mobicare.model.endereco.distrito;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by Voloide Tamele on 10/3/2017.
 */
public class DistritoDaoImpl extends BaseDaoImpl<Distrito, Integer> implements DistritoDao {
    public DistritoDaoImpl(Class<Distrito> dataClass) throws SQLException {
        super(dataClass);
    }

    public DistritoDaoImpl(ConnectionSource connectionSource, Class<Distrito> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DistritoDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Distrito> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

package mz.co.insystems.mobicare.model.farmaco;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by Voloide Tamele on 10/3/2017.
 */
public class FarmacoDaoImpl extends BaseDaoImpl<Farmaco, Integer> implements FarmacoDao {
    public FarmacoDaoImpl(Class<Farmaco> dataClass) throws SQLException {
        super(dataClass);
    }

    public FarmacoDaoImpl(ConnectionSource connectionSource, Class<Farmaco> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public FarmacoDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Farmaco> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

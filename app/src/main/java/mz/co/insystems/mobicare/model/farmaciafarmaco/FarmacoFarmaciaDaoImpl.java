package mz.co.insystems.mobicare.model.farmaciafarmaco;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

public class FarmacoFarmaciaDaoImpl extends BaseDaoImpl<FarmacoFarmacia, Integer> implements FarmacoFarmaciaDao {

    protected FarmacoFarmaciaDaoImpl(Class<FarmacoFarmacia> dataClass) throws SQLException {
        super(dataClass);
    }

    protected FarmacoFarmaciaDaoImpl(ConnectionSource connectionSource, Class<FarmacoFarmacia> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected FarmacoFarmaciaDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<FarmacoFarmacia> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

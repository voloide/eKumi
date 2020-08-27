package mz.co.insystems.service.ekumi.model.farmaciafarmaco;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.co.insystems.service.ekumi.base.AbstractBaseDAO;

public class FarmacoFarmaciaDaoImpl extends AbstractBaseDAO<FarmacoFarmacia, Integer> implements FarmacoFarmaciaDao {

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

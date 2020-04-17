package mz.co.insystems.mobicare.model.farmacia;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Voloide Tamele on 10/23/2017.
 */
public class FarmaciaDaoImpl extends BaseDaoImpl<Farmacia, Integer> implements FarmaciaDao {


    public FarmaciaDaoImpl(Class<Farmacia> dataClass) throws SQLException {
        super(dataClass);
    }

    public FarmaciaDaoImpl(ConnectionSource connectionSource, Class<Farmacia> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public FarmaciaDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Farmacia> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<Farmacia> searchByDescription(String description) throws SQLException {
        return (List<Farmacia>) queryBuilder().where().like(Farmacia.COLUMN_FARMACIA_NOME, description);
    }
}

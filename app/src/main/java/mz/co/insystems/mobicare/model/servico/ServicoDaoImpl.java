package mz.co.insystems.mobicare.model.servico;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by Voloide Tamele on 10/23/2017.
 */
public class ServicoDaoImpl extends BaseDaoImpl<Servico, Integer> implements ServicoDao {
    public ServicoDaoImpl(Class<Servico> dataClass) throws SQLException {
        super(dataClass);
    }

    public ServicoDaoImpl(ConnectionSource connectionSource, Class<Servico> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ServicoDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Servico> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

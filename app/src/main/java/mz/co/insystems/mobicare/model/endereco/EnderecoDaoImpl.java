package mz.co.insystems.mobicare.model.endereco;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by Voloide Tamele on 10/3/2017.
 */
public class EnderecoDaoImpl extends BaseDaoImpl<Endereco, Integer> implements EnderecoDao {
    public EnderecoDaoImpl(Class<Endereco> dataClass) throws SQLException {
        super(dataClass);
    }

    public EnderecoDaoImpl(ConnectionSource connectionSource, Class<Endereco> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public EnderecoDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Endereco> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

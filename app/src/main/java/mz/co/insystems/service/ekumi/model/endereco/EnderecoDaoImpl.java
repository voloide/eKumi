package mz.co.insystems.service.ekumi.model.endereco;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.co.insystems.service.ekumi.base.AbstractBaseDAO;

/**
 * Created by Voloide Tamele on 10/3/2017.
 */
public class EnderecoDaoImpl extends AbstractBaseDAO<Endereco, Integer> implements EnderecoDao {
    public EnderecoDaoImpl(Class<Endereco> dataClass) throws SQLException {
        super(dataClass);
    }

    public EnderecoDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Endereco.class);
    }

    public EnderecoDaoImpl(ConnectionSource connectionSource, Class<Endereco> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public EnderecoDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Endereco> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

package mz.co.insystems.service.ekumi.model.pessoa;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.co.insystems.service.ekumi.base.AbstractBaseDAO;

/**
 * Created by Voloide Tamele on 10/3/2017.
 */
public class PessoaDaoImpl extends AbstractBaseDAO<Pessoa, Integer> implements PessoaDao {


    public PessoaDaoImpl(Class<Pessoa> dataClass) throws SQLException {
        super(dataClass);
    }

    public PessoaDaoImpl(ConnectionSource connectionSource, Class<Pessoa> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PessoaDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Pessoa> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

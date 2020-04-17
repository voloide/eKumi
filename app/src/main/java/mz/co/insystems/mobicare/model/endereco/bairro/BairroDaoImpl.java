package mz.co.insystems.mobicare.model.endereco.bairro;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by Voloide Tamele on 10/3/2017.
 */
public class BairroDaoImpl extends BaseDaoImpl<Bairro, Integer> implements BairroDao {
    public BairroDaoImpl(Class<Bairro> dataClass) throws SQLException {
        super(dataClass);
    }

    public BairroDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Bairro.class);
    }

    public BairroDaoImpl(ConnectionSource connectionSource, Class<Bairro> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public BairroDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Bairro> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

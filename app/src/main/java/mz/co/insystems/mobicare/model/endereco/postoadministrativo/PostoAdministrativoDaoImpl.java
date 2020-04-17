package mz.co.insystems.mobicare.model.endereco.postoadministrativo;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by Voloide Tamele on 10/3/2017.
 */
public class PostoAdministrativoDaoImpl extends BaseDaoImpl<PostoAdministrativo, Integer> implements PostoAdministrativoDao {
    public PostoAdministrativoDaoImpl(Class<PostoAdministrativo> dataClass) throws SQLException {
        super(dataClass);
    }

    public PostoAdministrativoDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, PostoAdministrativo.class);
    }

    public PostoAdministrativoDaoImpl(ConnectionSource connectionSource, Class<PostoAdministrativo> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PostoAdministrativoDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<PostoAdministrativo> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

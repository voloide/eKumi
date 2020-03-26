package mz.co.insystems.mobicare.model.farmacia;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Voloide Tamele on 10/23/2017.
 */
public interface FarmaciaDao extends Dao<Farmacia, Integer> {

    List<Farmacia> searchByDescription(String description) throws SQLException;
}

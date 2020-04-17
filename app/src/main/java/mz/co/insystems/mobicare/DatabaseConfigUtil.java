package mz.co.insystems.mobicare;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

import mz.co.insystems.mobicare.model.contacto.Contacto;
import mz.co.insystems.mobicare.model.endereco.Endereco;
import mz.co.insystems.mobicare.model.endereco.bairro.Bairro;
import mz.co.insystems.mobicare.model.endereco.distrito.Distrito;
import mz.co.insystems.mobicare.model.endereco.municipio.Municipio;
import mz.co.insystems.mobicare.model.endereco.postoadministrativo.PostoAdministrativo;
import mz.co.insystems.mobicare.model.endereco.provincia.Provincia;
import mz.co.insystems.mobicare.model.farmacia.Farmacia;
import mz.co.insystems.mobicare.model.farmaciafarmaco.FarmacoFarmacia;
import mz.co.insystems.mobicare.model.farmaco.Farmaco;
import mz.co.insystems.mobicare.model.pessoa.Pessoa;
import mz.co.insystems.mobicare.model.search.RecentSearch;
import mz.co.insystems.mobicare.model.servico.Servico;
import mz.co.insystems.mobicare.model.user.User;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[] {
            User.class,
            Pessoa.class,
            Farmacia.class,
            Endereco.class,
            Contacto.class,
            Provincia.class,
            Distrito.class,
            Municipio.class,
            PostoAdministrativo.class,
            Bairro.class,
            Servico.class,
            Farmaco.class,
            RecentSearch.class,
            FarmacoFarmacia.class
    };

    public static void main(String[] args) throws SQLException, IOException {

        // Provide the name of .txt file which you have already created and kept in res/raw directory
        //writeConfigFile();
        writeConfigFile("ormlite_config.txt", classes);
    }
}

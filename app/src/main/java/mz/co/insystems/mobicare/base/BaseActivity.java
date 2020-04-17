package mz.co.insystems.mobicare.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.model.endereco.bairro.BairroDao;
import mz.co.insystems.mobicare.model.endereco.distrito.DistritoDao;
import mz.co.insystems.mobicare.model.endereco.municipio.MunicipioDao;
import mz.co.insystems.mobicare.model.endereco.postoadministrativo.PostoAdministrativoDao;
import mz.co.insystems.mobicare.model.endereco.provincia.ProvinciaDao;
import mz.co.insystems.mobicare.model.farmacia.FarmaciaDao;
import mz.co.insystems.mobicare.model.farmaco.FarmacoDao;
import mz.co.insystems.mobicare.model.search.RecentRearhDao;
import mz.co.insystems.mobicare.model.servico.ServicoDao;
import mz.co.insystems.mobicare.model.user.User;
import mz.co.insystems.mobicare.model.user.UserDao;
import mz.co.insystems.mobicare.sync.MobicareSyncService;
import mz.co.insystems.mobicare.util.Utilities;

public abstract class BaseActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper = null;
    protected String TAG;
    private User currentUser;

    private UserDao mUserDao;
    private ProvinciaDao provinciaDao;
    private DistritoDao distritoDao;
    private MunicipioDao municipioDao;
    private FarmaciaDao farmaciaDao;
    private FarmacoDao farmacoDao;
    private ServicoDao servicoDao;

    private PostoAdministrativoDao postoDao;
    private BairroDao bairroDao;

    private RecentRearhDao recentRearhDao;
    protected MobicareSyncService service;

    private ProgressDialog syncProgress;

    protected Utilities utilities;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public BaseActivity(){
        this.service = MobicareSyncService.getInstance();
        this.utilities = Utilities.getInstance();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void showLoading(Context context, String title, String msg) {
        syncProgress = ProgressDialog.show(context, title, msg);
        syncProgress.setCancelable(true);
    }

    public void hideLoading() {
        if (syncProgress != null && syncProgress.isShowing()) syncProgress.dismiss();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected Toolbar addHomeButtonToolbar(){
        return initToolbar(true, new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    protected Toolbar addSimpleToolbar(){
        return initToolbar(false, new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    private Toolbar initToolbar(boolean addHomeButton, ColorDrawable icon) {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.bringToFront();


        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(addHomeButton);
        actionbar.setHomeButtonEnabled(addHomeButton);
        actionbar.setIcon(icon);
        return toolbar;
    }

    public String buildGetAllUrlString(String entinty) {
        Uri.Builder uri =  service.initServiceUri();
        uri.appendPath(entinty);
        uri.appendPath(MobicareSyncService.SERVICE_GET_ALL);
        return uri.build().toString();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }


    protected DatabaseHelper getHelper(Context context) {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(context,DatabaseHelper.class);
        }
        return dbHelper;
    }

    protected void showMessageDialog(final String message) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public UserDao getmUserDao() {
        try {
            mUserDao = getHelper(getApplicationContext()).getUserDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mUserDao;
    }

    public ProvinciaDao getProvinciaDao() {
        try {
            provinciaDao = getHelper(getApplicationContext()).getProvinciaDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provinciaDao;
    }

    public DistritoDao getDistritoDao() {
        try {
            distritoDao = getHelper(getApplicationContext()).getDistritoDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distritoDao;
    }

    public MunicipioDao getMunicipioDao() {
        try {
            municipioDao = getHelper(getApplicationContext()).getMunicipioDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return municipioDao;
    }

    public FarmaciaDao getFarmaciaDao() {
        try {
            farmaciaDao = getHelper(getApplicationContext()).getFarmaciaDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmaciaDao;
    }

    public FarmacoDao getFarmacoDao() {
        try {
            farmacoDao = getHelper(getApplicationContext()).getFarmacoDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmacoDao;
    }

    public ServicoDao getServicoDao() {
        try {
            servicoDao = getHelper(getApplicationContext()).getServicoDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicoDao;
    }

    public PostoAdministrativoDao getPostoDao() {
        try {
            postoDao = getHelper(getApplicationContext()).getPostoAdministrativoDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postoDao;
    }

    public BairroDao getBairroDao() {
        try {
            bairroDao = getHelper(getApplicationContext()).getBairroDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bairroDao;
    }

    public RecentRearhDao getRecentRearhDao() {
        try {
            recentRearhDao = getHelper(getApplicationContext()).getRecentRearhDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recentRearhDao;
    }

    public MobicareSyncService getService() {
        return service;
    }

}

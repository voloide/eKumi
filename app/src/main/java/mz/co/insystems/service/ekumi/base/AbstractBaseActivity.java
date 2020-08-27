package mz.co.insystems.service.ekumi.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import mz.co.insystems.service.ekumi.sync.MobicareSyncService;
import mz.co.insystems.service.ekumi.util.Utilities;
import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.model.endereco.bairro.BairroDao;
import mz.co.insystems.service.ekumi.model.endereco.distrito.DistritoDao;
import mz.co.insystems.service.ekumi.model.endereco.municipio.MunicipioDao;
import mz.co.insystems.service.ekumi.model.endereco.postoadministrativo.PostoAdministrativoDao;
import mz.co.insystems.service.ekumi.model.endereco.provincia.ProvinciaDao;
import mz.co.insystems.service.ekumi.model.farmacia.FarmaciaDao;
import mz.co.insystems.service.ekumi.model.farmaco.FarmacoDao;
import mz.co.insystems.service.ekumi.model.search.RecentRearhDao;
import mz.co.insystems.service.ekumi.model.servico.ServicoDao;
import mz.co.insystems.service.ekumi.model.user.User;
import mz.co.insystems.service.ekumi.model.user.UserDao;

public abstract class AbstractBaseActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper = null;
    protected String TAG;
    protected AbstractBaseService operationService;

    protected AbstractBaseViewModel viewModel;
    
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getCurrentUser() == null) this.currentUser = new User();

        this.viewModel = initViewModel();
    }

    protected abstract AbstractBaseViewModel initViewModel();

    @Override
    protected void onResume() {
        super.onResume();
    }

    public AbstractBaseActivity(){
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
        if (operationService != null) operationService.releaseDBHelper();
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }

    public AbstractBaseService getOperationService() {
        return operationService;
    }

    public MobicareSyncService getService() {
        return service;
    }

}

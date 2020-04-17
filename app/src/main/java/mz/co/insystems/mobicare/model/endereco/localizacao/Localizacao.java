package mz.co.insystems.mobicare.model.endereco.localizacao;

import android.util.Log;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.activities.user.registration.fragment.PersonalDataFragment;
import mz.co.insystems.mobicare.activities.user.registration.fragment.view.PersonalDataFragmentView;
import mz.co.insystems.mobicare.base.BaseActivity;
import mz.co.insystems.mobicare.base.BaseVO;
import mz.co.insystems.mobicare.common.LocalizacaoObject;
import mz.co.insystems.mobicare.model.endereco.bairro.Bairro;
import mz.co.insystems.mobicare.model.endereco.distrito.Distrito;
import mz.co.insystems.mobicare.model.endereco.municipio.Municipio;
import mz.co.insystems.mobicare.model.endereco.postoadministrativo.PostoAdministrativo;
import mz.co.insystems.mobicare.model.endereco.provincia.Provincia;
import mz.co.insystems.mobicare.sync.MobicareSyncService;
import mz.co.insystems.mobicare.sync.SyncError;
import mz.co.insystems.mobicare.sync.VolleyResponseListener;
import mz.co.insystems.mobicare.util.Utilities;

/**
 * Created by Voloide Tamele on 11/27/2017.
 */

public class Localizacao extends BaseVO implements LocalizacaoSync {

    private static final long serialVersionUID = -3936872078922711026L;
    private Provincia selectedProvincia;
    private Distrito selectedDistrito;
    private Municipio selectedMunicipio;
    private PostoAdministrativo selectedPosto;
    private Bairro selectedBairro;

    private boolean isRural;

    private List<Provincia> provinciaList = new ArrayList<>();
    private List<Distrito> distritoList = new ArrayList<>();
    private List<Municipio> municipioList = new ArrayList<>();
    private List<Bairro> bairroList = new ArrayList<>();
    private List<PostoAdministrativo> postoList = new ArrayList<>();

    private BaseActivity currentActivity;
    private List<String> syncErrorList;

    private PersonalDataFragmentView fragmentView;

    public Localizacao(BaseActivity currentActivity, PersonalDataFragmentView fragmentView, final boolean isRural) {
        this.currentActivity = currentActivity;
        this.isRural = isRural;
        this.syncErrorList = new ArrayList<>();
        this.fragmentView = fragmentView;

        try {
            this.provinciaList = currentActivity.getProvinciaDao().queryForAll();
            if (provinciaList == null || provinciaList.size() <= 0) {
                doLocalizacaoSync(currentActivity, isRural);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fragmentView.reloadAllAdapters(this);
    }

    private void doLocalizacaoSync(BaseActivity currentActivity, final boolean isRural) {
        if (Utilities.isNetworkAvailable(this.currentActivity.getApplicationContext())) {
            fragmentView.showLoading();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    syncProvincias();
                    if (isRural) {
                        if (noSyncError()) syncDistritos();
                        if (noSyncError()) syncPostos();
                    } else {
                        if (noSyncError()) syncMunicipios();
                        if (noSyncError()) syncBairros();
                    }
                 }
            }).start();

        } else {
            Utilities.displayCommonAlertDialog(((PersonalDataFragment)fragmentView).getContext(), this.currentActivity.getString(R.string.network_not_available));
        }
    }

    private boolean noSyncError() {
        return syncErrorList == null || syncErrorList.isEmpty();
    }

    @Bindable
    public LocalizacaoObject getSelectedProvincia() {
        return selectedProvincia;
    }

    public void setSelectedProvincia(LocalizacaoObject selectedProvincia) {
        this.selectedProvincia = (Provincia) selectedProvincia;
        try {
            if (this.isRural){
                distritoList = currentActivity.getDistritoDao().queryBuilder().where().eq(Distrito.COLUMN_DISTRITO_PROVINCIA_ID, selectedProvincia.getId()).query();
            }else {
                municipioList = currentActivity.getMunicipioDao().queryBuilder().where().eq(Municipio.COLUMN_MUNICIPIO_PROVINCIA_ID, selectedProvincia.getId()).query();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        fragmentView.loadLevelTwoAdapters(this);
        resetLevelThreeData();
        fragmentView.loadLevelThreeAdapters(this);
        notifyPropertyChanged(BR.selectedProvincia);
    }

    public void resetLevelThreeData() {
        postoList = new ArrayList<>();
        bairroList = new ArrayList<>();
        selectedPosto = null;
        selectedBairro = null;
    }

    public void resetLevelTwoData() {
        distritoList = new ArrayList<>();
        municipioList = new ArrayList<>();
        selectedMunicipio = null;
        selectedDistrito = null;
    }

    @Bindable
    public List<Provincia> getProvinciaList() {
        return provinciaList;
    }

    public void setProvinciaList(List<Provincia> provinciaList) {
        this.provinciaList = provinciaList;
        notifyPropertyChanged(BR.provinciaList);
    }

    @Bindable
    public boolean isRural() {
        return isRural;
    }

    public void setRural(boolean rural) {
        isRural = rural;
        notifyPropertyChanged(BR.rural);
    }

    @Bindable
    public LocalizacaoObject getSelectedDistrito() {
        return selectedDistrito;
    }

    public void setSelectedDistrito(LocalizacaoObject selectedDistrito) {
        if (selectedDistrito instanceof Distrito) {
            this.selectedDistrito = (Distrito) selectedDistrito;
            resetLevelThreeData();
            try {
                postoList = currentActivity.getPostoDao().queryBuilder().where().eq(PostoAdministrativo.COLUMN_POSTO_DISTRITO_ID, this.selectedDistrito.getId()).query();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            fragmentView.loadLevelThreeAdapters(this);
            notifyPropertyChanged(BR.selectedDistrito);
        }
    }

    @Bindable
    public LocalizacaoObject getSelectedMunicipio() {
        return selectedMunicipio;
    }

    @Bindable
    public LocalizacaoObject getSelectedPosto() {
        return selectedPosto;
    }

    public void setSelectedPosto(LocalizacaoObject selectedPosto) {
        this.selectedPosto = (PostoAdministrativo) selectedPosto;
        if (fragmentView.getCurrentUser().isFarmacia()){
            fragmentView.getCurrentUser().getFarmacia().getEndereco().setPostoAdministrativo(this.selectedPosto);
            fragmentView.getCurrentUser().getFarmacia().getEndereco().setBairro(null);
        }else {
            fragmentView.getCurrentUser().getPessoa().getEndereco().setPostoAdministrativo(this.selectedPosto);
            fragmentView.getCurrentUser().getFarmacia().getEndereco().setBairro(null);
        }
        notifyPropertyChanged(BR.selectedPosto);
    }

    @Bindable
    public LocalizacaoObject getSelectedBairro() {
        return selectedBairro;
    }

    public void setSelectedBairro(LocalizacaoObject selectedBairro) {
        if (selectedBairro instanceof Bairro) {
            this.selectedBairro = (Bairro) selectedBairro;
            if (fragmentView.getCurrentUser().isFarmacia()){
                fragmentView.getCurrentUser().getFarmacia().getEndereco().setBairro(this.selectedBairro);
                fragmentView.getCurrentUser().getFarmacia().getEndereco().setPostoAdministrativo(null);
            }else {
                fragmentView.getCurrentUser().getPessoa().getEndereco().setBairro(this.selectedBairro);
                fragmentView.getCurrentUser().getPessoa().getEndereco().setPostoAdministrativo(null);
            }
            notifyPropertyChanged(BR.selectedBairro);
        }
    }

    public void setSelectedMunicipio(LocalizacaoObject selectedMunicipio){
        if (selectedMunicipio instanceof Municipio) {
            this.selectedMunicipio = (Municipio) selectedMunicipio;
            resetLevelThreeData();
            try {
                bairroList = currentActivity.getBairroDao().queryBuilder().where().eq(Bairro.COLUMN_BAIRRO_MUNICIPIO, this.selectedMunicipio.getId()).query();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            fragmentView.loadLevelThreeAdapters(this);
            notifyPropertyChanged(BR.selectedMunicipio);
        }
    }

    @Bindable
    public List<Distrito> getDistritoList() {
        return distritoList;
    }

    public void setDistritoList(List<Distrito> distritoList) {
        this.distritoList = distritoList;
        notifyPropertyChanged(BR.distritoList);
    }

    @Bindable
    public List<Municipio> getMunicipioList() {
        return municipioList;
    }

    public void setMunicipioList(List<Municipio> municipioList) {
        this.municipioList = municipioList;
        notifyPropertyChanged(BR.municipioList);
    }

    @Bindable
    public List<Bairro> getBairroList() {
        return bairroList;
    }

    public void setBairroList(List<Bairro> bairroList) {
        this.bairroList = bairroList;
        notifyPropertyChanged(BR.bairroList);
    }

    @Bindable
    public List<PostoAdministrativo> getPostoList() {
        return postoList;
    }

    public void setPostoList(List<PostoAdministrativo> postoList) {
        this.postoList = postoList;
        notifyPropertyChanged(BR.postoList);
    }

    @Override
    public void syncProvincias() {
        getService().makeJsonArrayRequest(Request.Method.GET, currentActivity.buildGetAllUrlString(Provincia.TABLE_NAME_PROVINCIA), null, currentActivity.getCurrentUser(), new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                onSyncError(Provincia.TABLE_NAME_PROVINCIA, null);
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {}

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {

                for(int i=0;i<response.length();i++){
                    try {
                        Provincia provincia = utilities.fromJsonObject(response.getJSONObject(i), Provincia.class);
                        currentActivity.getProvinciaDao().createIfNotExists(provincia);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private MobicareSyncService getService() {
        return currentActivity.getService();
    }

    @Override
    public void syncDistritos() {
        getService().makeJsonArrayRequest(Request.Method.GET, currentActivity.buildGetAllUrlString(Distrito.TABLE_NAME_DISTRITO), null, currentActivity.getCurrentUser(), new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                onSyncError(Distrito.TABLE_NAME_DISTRITO, null);
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {}

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {

                for(int i=0;i<response.length();i++){
                    try {
                        Distrito distrito = utilities.fromJsonObject(response.getJSONObject(i), Distrito.class);
                        currentActivity.getDistritoDao().createIfNotExists(distrito);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void syncPostos() {
        getService().makeJsonArrayRequest(Request.Method.GET, currentActivity.buildGetAllUrlString(PostoAdministrativo.TABLE_NAME_POSTO), null, currentActivity.getCurrentUser(), new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                onSyncError(PostoAdministrativo.TABLE_NAME_POSTO, null);
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {}

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {

                for(int i=0;i<response.length();i++){
                    try {
                        PostoAdministrativo postoAdministrativo = utilities.fromJsonObject(response.getJSONObject(i), PostoAdministrativo.class);
                        currentActivity.getPostoDao().createIfNotExists(postoAdministrativo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                onSyncFinished();
            }
        });
    }

    private void onSyncFinished() {
        fragmentView.hideLoading();
        try {
            provinciaList = currentActivity.getProvinciaDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fragmentView.reloadAllAdapters(this);
    }

    @Override
    public void syncMunicipios() {
        getService().makeJsonArrayRequest(Request.Method.GET, currentActivity.buildGetAllUrlString(Municipio.TABLE_NAME_MUNICIPIO), null, currentActivity.getCurrentUser(), new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                onSyncError(Municipio.TABLE_NAME_MUNICIPIO, null);
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {}

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {

                for(int i=0;i<response.length();i++){
                    try {
                        Municipio municipio = utilities.fromJsonObject(response.getJSONObject(i), Municipio.class);
                        currentActivity.getMunicipioDao().createIfNotExists(municipio);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void syncBairros() {
        getService().makeJsonArrayRequest(Request.Method.GET, currentActivity.buildGetAllUrlString(Bairro.TABLE_NAME_BAIRRO), null, currentActivity.getCurrentUser(), new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                onSyncError(Distrito.TABLE_NAME_DISTRITO, null);
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {}

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {

                for(int i=0;i<response.length();i++){
                    try {
                        Bairro bairro = utilities.fromJsonObject(response.getJSONObject(i), Bairro.class);
                        currentActivity.getBairroDao().createIfNotExists(bairro);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                onSyncFinished();
            }
        });
    }

    private void onSyncError(String tableNameDistrito, String message) {
        fragmentView.hideLoading();
        if (this.syncErrorList == null) this.syncErrorList = new ArrayList<>();
        this.syncErrorList.add(message);
        Log.d("LOCALIZACAO", message.toString());

        Utilities.displayCommonAlertDialog(((PersonalDataFragment)fragmentView).getContext(), syncErrorList.toString());
    }
}
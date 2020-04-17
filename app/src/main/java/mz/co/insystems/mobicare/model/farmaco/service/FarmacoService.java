package mz.co.insystems.mobicare.model.farmaco.service;

import android.net.Uri;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import mz.co.insystems.mobicare.model.farmaco.Farmaco;
import mz.co.insystems.mobicare.model.user.User;
import mz.co.insystems.mobicare.sync.MobicareSyncService;
import mz.co.insystems.mobicare.sync.SyncError;
import mz.co.insystems.mobicare.sync.VolleyResponseListener;
import mz.co.insystems.mobicare.util.Utilities;

public class FarmacoService implements Runnable {
    private List<Farmaco> farmacos;
    private int serviceStatus;
    private User user;
    protected MobicareSyncService service;
    private Uri.Builder uri;
    private Farmaco selectedFarmaco;
    protected Utilities utilities;

    public FarmacoService(User user) {
        this.user = user;
        this.utilities = Utilities.getInstance();

        this.service = MobicareSyncService.getInstance();

    }

    @Override
    public void run() {

    }

    private void getFarmacoById(long farmacoId) {
        this.uri = service.initServiceUri();
        uri.appendPath(Farmaco.TABLE_NAME_FARMACO);
        uri.appendPath("getById");
        uri.appendPath(String.valueOf(farmacoId));

        final String url = uri.build().toString();


        this.service.makeJsonObjectRequest(Request.Method.GET, url, null, this.user, new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {
                try {
                    selectedFarmaco = utilities.fromJsonObject(response, Farmaco.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {
            }
        });
    }

    private void searchFarmacos(Uri.Builder uri, String farmacoDescription, double latitude, double longitude, String searchRange) {
        uri.appendPath(Farmaco.TABLE_NAME_FARMACO);
        uri.appendPath(MobicareSyncService.SERVICE_SEARCH);
        uri.appendPath(farmacoDescription);
        uri.appendPath(searchRange);
        uri.appendPath(String.valueOf(latitude));
        uri.appendPath(String.valueOf(longitude));

        final String url = uri.build().toString();

        this.farmacos = new ArrayList<>();

        this.service.makeJsonArrayRequest(Request.Method.GET, url, null, user, new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {

            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) { }

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {
                for(int i=0;i<response.length();i++){

                    try {
                        if (response.getJSONObject(i).has("message")){

                        }else {
                            farmacos.add(utilities.fromJsonObject(response.getJSONObject(i), Farmaco.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

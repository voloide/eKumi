package mz.co.insystems.service.ekumi.model.farmaco.service;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mz.co.insystems.service.ekumi.base.AbstractEkumiWebService;
import mz.co.insystems.service.ekumi.sync.MobicareSyncService;
import mz.co.insystems.service.ekumi.sync.SyncError;
import mz.co.insystems.service.ekumi.sync.VolleyResponseListener;
import mz.co.insystems.service.ekumi.model.farmaco.Farmaco;
import mz.co.insystems.service.ekumi.model.user.User;

public class FarmacoWebService extends AbstractEkumiWebService {
    private List<Farmaco> farmacos;

    private User user;

    private Farmaco requestData;
    private double latitude;
    private double longitude;
    private String searchRange;
    private int page;

    private static FarmacoWebService instance;

    public FarmacoWebService(User user, Context context) {
        super(context);
        this.user = user;

        this.service = MobicareSyncService.getInstance();

        this.farmacos = new ArrayList<>();
    }

    public static FarmacoWebService getInstance(User user, Context context){
        if (instance == null){
            instance = new FarmacoWebService(user, context);
        }

        return instance;
    }

    @Override
    public void run() {

        this.serviceStatus = STATUS_RUNNING;

        if (requestedService.equals(GET_BY_ID)){
            getFarmacoById(requestData.getId());
        }else
        if (requestedService.equals(SEARCH)){
            searchFarmacos();
        }

        while (isRunning()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.searchResult.setQtdAllRecords(this.farmacos.size());
        this.searchResult.setCurrentSearchedRecords(this.farmacos);

    }

    private void getFarmacoById(long farmacoId) {
        Uri.Builder uri =  getService().initServiceUri();
        uri.appendPath(Farmaco.TABLE_NAME_FARMACO);
        uri.appendPath("getById");
        uri.appendPath(String.valueOf(farmacoId));

        final String url = uri.build().toString();


        this.service.makeJsonObjectRequest(Request.Method.GET, url, null, this.user, new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                errorMsg = error.getErrorMessage();
                serviceStatus = STATUS_FINISHED_WITH_ERROR;
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {
                try {
                    farmacos.add(utilities.fromJsonObject(response, Farmaco.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {
            }
        });
    }



    private void searchFarmacos() {
        Uri.Builder uri =  getService().initServiceUri().appendPath(Farmaco.TABLE_NAME_FARMACO)
        .appendPath(MobicareSyncService.SERVICE_SEARCH).appendPath(requestData.getDesignacao()).appendPath(searchRange)
        .appendPath(String.valueOf(latitude)).appendPath(String.valueOf(longitude)).appendPath(String.valueOf(page));

        final String url = uri.build().toString();

        this.service.makeJsonObjectRequest(Request.Method.GET, url, null, user, new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                errorMsg = error.getErrorMessage();
                serviceStatus = STATUS_FINISHED_WITH_ERROR;
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {
                if (response != null){
                    //Log.println()
                }
            }

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {
                for(int i=0;i<response.length();i++){

                    try {
                        if (response.getJSONObject(i).has("message")){
                            resourceStatus = RESOURCE_STATUS_NOT_AVAILABLE;
                        }else {
                            farmacos.add(utilities.fromJsonObject(response.getJSONObject(i), Farmaco.class));
                        }
                        serviceStatus = STATUS_FINISHED;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public Farmaco getRequestData() {
        return requestData;
    }

    public FarmacoWebService setRequestData(Farmaco requestData) {
        this.requestData = requestData;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public FarmacoWebService setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public FarmacoWebService setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getSearchRange() {
        return searchRange;
    }

    public FarmacoWebService setSearchRange(String searchRange) {
        this.searchRange = searchRange;
        return this;
    }

    public int getPage() {
        return page;
    }

    public FarmacoWebService setPage(int page) {
        this.page = page;
        return this;
    }
}

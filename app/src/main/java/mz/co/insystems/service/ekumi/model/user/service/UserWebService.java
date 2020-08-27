package mz.co.insystems.service.ekumi.model.user.service;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mz.co.insystems.service.ekumi.base.AbstractEkumiWebService;
import mz.co.insystems.service.ekumi.common.SyncStatus;
import mz.co.insystems.service.ekumi.model.user.User;
import mz.co.insystems.service.ekumi.sync.MobicareSyncService;
import mz.co.insystems.service.ekumi.sync.ServiceResponseListener;
import mz.co.insystems.service.ekumi.sync.SyncError;
import mz.co.insystems.service.ekumi.sync.VolleyResponseListener;
import mz.co.insystems.service.ekumi.util.Utilities;

public class UserWebService extends AbstractEkumiWebService {

    public static String GET_BY_CREDENTIALS = "GET_BY_CREDENTIALS";
    public static String UPDATE_LOGIN_STATUS = "UPDATE_LOGIN_STATUS";
    public static String RESET_PIN = "RESET_PIN";
    public static String SERVICE_CHECK_MOBILE_NUMBER_AVAILABILITY = "SERVICE_CHECK_MOBILE_NUMBER_AVAILABILITY";

    private User resultUser;

    private User requestData;

    private static UserWebService instance;

    private UserWebService(Context context) {
        super(context);

        service = MobicareSyncService.getInstance();
    }

    public static UserWebService getInstance(Context context){
        if (instance == null){
            instance = new UserWebService(context);
        }

        return instance;
    }

    @Override
    public void run() {
        resultUser = null;
        resourceStatus = null;

        this.serviceStatus = STATUS_RUNNING;

        if (requestedService.equals(GET_BY_CREDENTIALS)){
            getUserFromWeb();
        }else if (requestedService.equals(UPDATE_LOGIN_STATUS)){
            tryToUpdateLoginStatusOnWeb();
        }else if (requestedService.equals(SERVICE_CHECK_MOBILE_NUMBER_AVAILABILITY)){
            checkMobileNumber();
        }if (requestedService.equals(RESET_PIN)){
            resetPin();
        }
    }

    public String getRequestedService() {
        return requestedService;
    }

    public void setRequestedService(String requestedService) {
        this.requestedService = requestedService;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public User getResultUser() {
        return resultUser;
    }

    public void setResultUser(User resultUser) {
        this.resultUser = resultUser;
    }

    public User getRequestData() {
        return requestData;
    }

    public void setRequestData(User requestData) {
        this.requestData = requestData;
    }

    private void getUserFromWeb(){

        Uri.Builder uri =  getService().initServiceUri();
        uri.appendPath(User.TABLE_NAME);
        uri.appendPath(MobicareSyncService.URL_SERVICE_USER_GET_BY_CREDENTIALS);
        uri.appendPath(requestData.getUserName());
        uri.appendPath(Utilities.MD5Crypt(requestData.getPassword()));
        final String url = uri.build().toString();

        ServiceResponseListener responseListener = new ServiceResponseListener();
        try {
            getService().makeJsonObjectRequest(Request.Method.GET, url, requestData.parseToJsonObject(), requestData, new VolleyResponseListener() {
                @Override
                public void onError(SyncError error) {
                    errorMsg = error.getErrorMessage();
                    serviceStatus = STATUS_FINISHED_WITH_ERROR;
                }

                @Override
                public void onResponse(JSONObject response, int myStatusCode) {
                    try {
                        searchResult.getCurrentSearchedRecords().add(utilities.fromJsonObject(response, User.class));
                        //resultUser = utilities.fromJsonObject(response, User.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    serviceStatus = STATUS_FINISHED;
                }

                @Override
                public void onResponse(JSONArray response, int myStatusCode) {}
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void tryToUpdateLoginStatusOnWeb() {

        Uri.Builder uri = service.initServiceUri();
        uri.appendPath(MobicareSyncService.SERVICE_ENTITY_USER);
        uri.appendPath(MobicareSyncService.SERVICE_AUTHENTICATE);
        final String url = uri.build().toString();

        service.makeJsonObjectRequest(Request.Method.POST, url, null, requestData, new VolleyResponseListener() {

            @Override
            public void onError(SyncError error) {
                errorMsg = error.getErrorMessage();
                serviceStatus = STATUS_FINISHED_WITH_ERROR;
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {
                serviceStatus = STATUS_FINISHED;
            }

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {}
        });
    }

    private void createUser(){
        Uri.Builder uri =  getService().initServiceUri();
        uri.appendPath(User.TABLE_NAME);
        uri.appendPath(MobicareSyncService.SERVICE_CREATE);
        final String url = uri.build().toString();

        try {
            getService().makeJsonObjectRequest(Request.Method.PUT, url, requestData.parseToJsonObject(), requestData, new VolleyResponseListener() {

                @Override
                public void onError(SyncError error) {
                    errorMsg = error.getErrorMessage();
                    serviceStatus = STATUS_FINISHED_WITH_ERROR;
                    setResourceStatus(RESOURCE_STATUS_CHECK_ERROR);
                }

                @Override
                public void onResponse(JSONObject response, int myStatusCode) {
                    try {
                        SyncStatus syncStatus = utilities.fromJsonObject(response, SyncStatus.class);
                        if (syncStatus.getCode() == 100){
                            setResourceStatus(RESOURCE_STATUS_AVAILABLE);
                        }else {
                            setResourceStatus(RESOURCE_STATUS_NOT_AVAILABLE);
                        }
                        serviceStatus = STATUS_FINISHED;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponse(JSONArray response, int myStatusCode) {}
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkMobileNumber() {

        Uri.Builder uri = getService().initServiceUri();
        uri.appendPath(User.TABLE_NAME);
        uri.appendPath(MobicareSyncService.SERVICE_CHECK_MOBILE_NUMBER_AVAILABILITY);
        uri.appendPath(requestData.getMobileNmber());
        final String url = uri.build().toString();

        try {
            getService().makeJsonObjectRequest(Request.Method.GET, url, requestData.parseToJsonObject(), requestData, new VolleyResponseListener() {
                @Override
                public void onError(SyncError error) {
                    errorMsg = error.getErrorMessage();
                    serviceStatus = STATUS_FINISHED_WITH_ERROR;
                    setResourceStatus(RESOURCE_STATUS_CHECK_ERROR);
                }

                @Override
                public void onResponse(JSONObject response, int myStatusCode) {
                    try {
                        SyncStatus syncStatus = utilities.fromJsonObject(response, SyncStatus.class);
                        if (syncStatus.getCode() == 100){
                            setResourceStatus(RESOURCE_STATUS_AVAILABLE);
                        }else {
                            setResourceStatus(RESOURCE_STATUS_NOT_AVAILABLE);
                        }
                        serviceStatus = STATUS_FINISHED;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onResponse(JSONArray response, int myStatusCode) {
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void resetPin() {

        Uri.Builder uri = getService().initServiceUri();
        uri.appendPath(User.TABLE_NAME);
        uri.appendPath(MobicareSyncService.SERVICE_RESET_PIN);
        uri.appendPath(requestData.getMobileNmber());
        final String url = uri.build().toString();

        try {
            getService().makeJsonObjectRequest(Request.Method.POST, url, requestData.parseToJsonObject(), requestData, new VolleyResponseListener() {
                @Override
                public void onError(SyncError error) {
                    errorMsg = error.getErrorMessage();
                    serviceStatus = STATUS_FINISHED_WITH_ERROR;
                    setResourceStatus(RESOURCE_STATUS_CHECK_ERROR);
                }

                @Override
                public void onResponse(JSONObject response, int myStatusCode) {
                    try {
                        SyncStatus syncStatus = utilities.fromJsonObject(response, SyncStatus.class);
                        if (syncStatus.getCode() == 100){
                            setResourceStatus(RESOURCE_STATUS_AVAILABLE);
                        }else {
                            setResourceStatus(RESOURCE_STATUS_NOT_AVAILABLE);
                        }
                        serviceStatus = STATUS_FINISHED;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onResponse(JSONArray response, int myStatusCode) {
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

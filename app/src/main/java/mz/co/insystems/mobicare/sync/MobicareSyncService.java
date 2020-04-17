package mz.co.insystems.mobicare.sync;

import android.net.Uri;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import mz.co.insystems.mobicare.model.user.User;
import mz.co.insystems.mobicare.util.Utilities;

public class MobicareSyncService {
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_FAIL = -1;
    public static final int RESULT_OK = 1;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    public static final String API_VERSION      = "v1.0";
    public static final String URI_AUTHORITY    = "mobicare.insystems.co.mz/"+API_VERSION;

    public static final String URI_AUTHORITY_TEST = "192.168.2.52";

    public static final String URL_SERVICE_USER_GET_BY_CREDENTIALS	= "getByCredentials";
    public static final String SERVICE_ENTITY_USER = User.TABLE_NAME;

    public static final String SERVICE_CREATE       = "create";
    public static final String SERVICE_GET_BY_ID    = "getById";
    public static final String SERVICE_GET_ALL      = "getAll";
    public static final String SERVICE_SEARCH       = "search";

    public static final String JSON_OBJECT_REQUEST_TAG  = "json_obj_req";
    public static final String JSON_ARRAY_REQUEST_TAG   = "json_array_req";
    public static final String SERVICE_AUTHENTICATE     = "authenticate";
    public static final String SERVICE_CHECK_USER_NAME_AVAILABILITY = "isUserNameAvailable";
    public static final long TWENTY_SECONDS = 40000;

    private Utilities utilities = Utilities.getInstance();

    private static int myStatusCode;
    private boolean noSyncError;
    private boolean syncOperationDone;

    private static Uri.Builder uriBuilder;
    protected static MobicareSyncService instance;

    public static MobicareSyncService getInstance(){
        if (instance == null){
            instance = new MobicareSyncService();
        }

        return instance;
    }

    private static Uri.Builder getUriBuilder(){
        return new Uri.Builder();
    }

    public Uri.Builder initServiceUri(){
        Uri.Builder uri =  getUriBuilder();
        uri.scheme("http");
        uri.authority(URI_AUTHORITY_TEST);
        uri.appendPath("mobicare");
        uri.appendPath("index.php");
        return uri;
    }

    /**
     * @param method request Method
     * @param url url
     * @param requestBody request Params
     * @param user current user for authentication
     * @param listener listener
     */
    public void makeJsonArrayRequest(final int method, String url, final JSONObject requestBody , final User user, final VolleyResponseListener listener) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(method, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                noSyncError = false;
                syncOperationDone = true;
                listener.onResponse(response, myStatusCode);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                noSyncError = true;
                syncOperationDone = true;
                listener.onError(generateErrorMsg(error, myStatusCode));
            }

        }) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    myStatusCode = response.statusCode;
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(new JSONArray(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return buildAuthHeaders(user);
            }
        };

        // Access the RequestQueue through singleton class.
        NetworkController.getInstance().addToRequestQueue(jsonArrayRequest, JSON_ARRAY_REQUEST_TAG);
    }

    private SyncError generateErrorMsg(VolleyError error, int myStatusCode){
        if (error instanceof NetworkError) {
            return new SyncError(myStatusCode, "A network error as occured ");
        } else if (error instanceof ServerError) {
            return new SyncError(myStatusCode, "A server error as occured ");
        } else if (error instanceof AuthFailureError) {
            return new SyncError(myStatusCode, "An authentication error as occured ");
        } else if (error instanceof ParseError) {
            return new SyncError(myStatusCode, "A parse error as occured ");
        } else if (error instanceof NoConnectionError) {
            return new SyncError(myStatusCode, "No connection ");
        } else if (error instanceof TimeoutError) {
            return new SyncError(myStatusCode, "Connection timeout");
        }
        return new SyncError(myStatusCode, error.toString());
    }

    /**
     * @param method request Method
     * @param url url
     * @param requestBody request Params
     * @param user current user for authentication
     * @param listener listener
     */
    public void makeJsonObjectRequest(final int method, String url, final JSONObject requestBody , final User user, final VolleyResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (method, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        noSyncError = true;
                        syncOperationDone = true;
                        listener.onResponse(response, myStatusCode);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        noSyncError = false;
                        syncOperationDone = true;
                        listener.onError(generateErrorMsg(error, myStatusCode));
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    myStatusCode = response.statusCode;
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (user.getId() <= 0) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(User.COLUMN_USER_NAME, user.getUserName());
                    params.put(User.COLUMN_PASSWORD, Utilities.MD5Crypt(user.getPassword()));
                    return params;
                }else return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return buildAuthHeaders(user);
            }
        };

        // Access the RequestQueue through singleton class.
        NetworkController.getInstance().addToRequestQueue(jsonObjectRequest, JSON_OBJECT_REQUEST_TAG);
    }

    public static Map<String, String> buildAuthHeaders(User user){

        Map<String, String> headerMap = new HashMap<>();
        String credentials = user.getUserName() + ":" + user.getPassword();
        String base64EncodedCredentials =
                Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Authorization", "Basic " + base64EncodedCredentials);
        return headerMap;
    }

    public boolean noSyncError() {
        return noSyncError;
    }

    public boolean syncOperationDone() {
        return syncOperationDone;
    }
}

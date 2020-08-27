package mz.co.insystems.service.ekumi.base;

import android.content.Context;

import mz.co.insystems.service.ekumi.sync.MobicareSyncService;
import mz.co.insystems.service.ekumi.util.Utilities;

public abstract class AbstractEkumiWebService<T extends AbstractBaseVO> implements Runnable{

    public static String STATUS_RUNNING = "STATUS_RUNNING";
    public static String STATUS_FINISHED = "STATUS_FINISHED";
    public static String STATUS_FINISHED_WITH_ERROR = "STATUS_FINISHED_WITH_ERROR";

    public static String RESOURCE_STATUS_AVAILABLE = "RESOURCE_STATUS_AVAILABLE";
    public static String RESOURCE_STATUS_NOT_AVAILABLE = "RESOURCE_STATUS_NOT_AVAILABLE";
    public static String RESOURCE_STATUS_CHECK_ERROR = "RESOURCE_STATUS_CHECK_ERROR";

    public static String GET_BY_ID = "GET_BY_ID";
    public static String SEARCH = "SEARCH";

    protected SearchResult<T> searchResult;

    protected Context context;

    protected T requestData;

    protected String requestedService;

    protected String serviceStatus;

    protected String errorMsg;

    protected Utilities utilities;

    protected MobicareSyncService service;

    protected String resourceStatus;

    public AbstractEkumiWebService(Context context) {
        this.context = context;
        this.service = MobicareSyncService.getInstance();
        this.utilities = Utilities.getInstance();
        this.searchResult = new SearchResult<T>();

    }

    /*public <T extends AbstractBaseVO> void addToSearchResult(T baseVO, Class clazz){
        this.searchResult.setCurrentSearchedRecords(Utilities.parseList(Utilities.parseToList(baseVO), clazz));
    }
*/

    public T getRequestData() {
        return requestData;
    }

    public void setRequestData(T requestData) {
        this.requestData = requestData;
    }

    public MobicareSyncService getService() {
        return service;
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

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public boolean isRunning(){
        return this.serviceStatus.equals(STATUS_RUNNING);
    }

    public boolean isFinished(){
        return this.serviceStatus.equals(STATUS_FINISHED);
    }

    public boolean isFinishedWithError(){
        return this.serviceStatus.equals(STATUS_FINISHED_WITH_ERROR);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public boolean isResourdeAvailable() {
        return this.resourceStatus.equals(RESOURCE_STATUS_AVAILABLE);
    }

    public boolean isResourdeNotAvailable() {
        return this.resourceStatus.equals(RESOURCE_STATUS_NOT_AVAILABLE);
    }

    public boolean isResourdeCheckedWithError() {
        return this.resourceStatus.equals(RESOURCE_STATUS_CHECK_ERROR);
    }

    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public String getResourceStatus() {
        return resourceStatus;
    }

    public SearchResult<T> getSearchResult() {
        return searchResult;
    }

    public boolean hasResults(){
        return Utilities.listHasElements(getSearchResult().getCurrentSearchedRecords());
    }

}

package mz.co.insystems.service.ekumi.base;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import mz.co.insystems.service.ekumi.util.Utilities;

public abstract class AbstractBaseService {

    protected DatabaseHelper dbHelper;
    protected Context context;
    protected Utilities utilities;
    protected AbstractEkumiWebService webService;

    protected ThreadPoolExecutor poolExecutor;

    public AbstractBaseService(Context context) {
        this.context = context;


        utilities = Utilities.getInstance();
        dbHelper = DatabaseHelper.getInstance(context);

        poolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    protected DatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public void releaseDBHelper(){
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }

    protected boolean isResourceAvailable(AbstractBaseVO baseVO, String requestedService){
        request(baseVO, requestedService);
        if (getWebService().isFinished()){
            if (getWebService().isResourdeAvailable()){
                return true;
            }
        }else if (getWebService().isFinishedWithError()){
            throw new RuntimeException(getWebService().getErrorMsg());
        }
        return false;
    }

    protected SearchResult makeDataRequest(AbstractBaseVO baseVO, String requestedService){
        request(baseVO, requestedService);

        if (getWebService().isFinished()){
            if (getWebService().hasResults()) {
                return getWebService().getSearchResult();
            }
        }else if (getWebService().isFinishedWithError()){
            throw new RuntimeException(getWebService().getErrorMsg());
        }
        return null;
    }

    private void request(AbstractBaseVO baseVO, String requestedService) {
        getWebService().setRequestData(baseVO);
        getWebService().setRequestedService(requestedService);

        this.poolExecutor.execute(getWebService());

        while (getWebService().getServiceStatus() == null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (getWebService().isRunning()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public boolean isNetworkAvailable(){
        return Utilities.isNetworkAvailable(getContext());
    }

    public AbstractEkumiWebService getWebService() {
        return webService;
    }

    public void setWebService(AbstractEkumiWebService webService) {
        this.webService = webService;
    }

    public boolean resourceAvailable(){
        return this.webService.isResourdeAvailable();
    }
}

package mz.co.insystems.service.ekumi.model.farmaco.service;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import mz.co.insystems.service.ekumi.base.AbstractBaseService;
import mz.co.insystems.service.ekumi.base.SearchResult;
import mz.co.insystems.service.ekumi.model.farmaco.Farmaco;
import mz.co.insystems.service.ekumi.model.farmaco.FarmacoDao;
import mz.co.insystems.service.ekumi.model.user.User;
import mz.co.insystems.service.ekumi.util.Utilities;

public class FarmacoService extends AbstractBaseService {

    private FarmacoDao farmacoDao;
    private FarmacoWebService webService;
    private User user;
    private static FarmacoService instance;

    public FarmacoService(User user, Context context) {
        super(context);
        this.user = user;
        try {
            farmacoDao = getDbHelper().getFarmacoDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        webService = FarmacoWebService.getInstance(this.user, context);
    }

    public static FarmacoService getInstance(User user, Context context){
        if (instance == null){
            instance = new FarmacoService(user, context);
        }

        return instance;
    }

    @Override
    public FarmacoWebService getWebService() {
        return webService;
    }

    public Farmaco getById(Farmaco farmaco) {
        SearchResult<Farmaco> searchResult = makeDataRequest(farmaco, FarmacoWebService.GET_BY_ID);

        if (!Utilities.listHasElements(searchResult.getCurrentSearchedRecords())) return null;

        return searchResult.getCurrentSearchedRecords().get(0);
    }

    public List<Farmaco> search(Farmaco requestData, double latitude, double longitude, String searchRange, int page){
        getWebService().setRequestData(requestData).setLatitude(latitude).setLongitude(longitude).setPage(page).setSearchRange(searchRange);

        SearchResult<Farmaco> searchResult = makeDataRequest(requestData, FarmacoWebService.SEARCH);

        return searchResult.getCurrentSearchedRecords();
    }
}

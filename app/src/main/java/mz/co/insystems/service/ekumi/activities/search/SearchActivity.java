package mz.co.insystems.service.ekumi.activities.search;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.google.android.material.tabs.TabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mz.co.insystems.service.ekumi.base.AbstractBaseActivity;
import mz.co.insystems.service.ekumi.base.AbstractBaseViewModel;
import mz.co.insystems.service.ekumi.common.FragmentChangeListener;
import mz.co.insystems.service.ekumi.databinding.ActivityMainSearchBinding;
import mz.co.insystems.service.ekumi.model.farmaco.service.FarmacoService;
import mz.co.insystems.service.ekumi.sync.MobicareSyncService;
import mz.co.insystems.service.ekumi.sync.SyncError;
import mz.co.insystems.service.ekumi.sync.VolleyResponseListener;
import mz.co.insystems.service.ekumi.util.Utilities;
import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.model.farmaco.Farmaco;
import mz.co.insystems.service.ekumi.model.search.Searchble;
import mz.co.insystems.service.ekumi.model.user.User;

import static mz.co.insystems.service.ekumi.base.AbstractEkumiWebService.RESOURCE_STATUS_NOT_AVAILABLE;
import static mz.co.insystems.service.ekumi.base.AbstractEkumiWebService.STATUS_FINISHED;
import static mz.co.insystems.service.ekumi.base.AbstractEkumiWebService.STATUS_FINISHED_WITH_ERROR;
import static mz.co.insystems.service.ekumi.base.AbstractEkumiWebService.STATUS_RUNNING;

public class SearchActivity extends AbstractBaseActivity implements LocationListener, FragmentChangeListener,Runnable {

    private MaterialSearchView searchView;
    private Thread searchThread;
    private String searchQuery;
    private boolean farmaciaSearchDone;
    private boolean servicoSearchDone;
    private boolean farmacoSearchDone;
    private String errorMsg;
    private String serviceStatus;
    private String resourceStatus;
    private int currentSearchPage;

    @Override
    public boolean isActivityTransitionRunning() {
        return super.isActivityTransitionRunning();
    }

    private List<Searchble> searchbleList;


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private SearchFragment searchFragment;
    private RecentSearchFragment recentSearchFragment;
    private HomeFragment homeFragment;

    private boolean mLocationPermissionGranted;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected double latitude;
    protected double longitude;

    protected FarmacoService farmacoService;

    private FragmentManager manager;


    @Override
    protected AbstractBaseViewModel initViewModel() {
        return null;
    }

    public SearchActivity() {
        this.searchbleList = new ArrayList<>();
        this.searchFragment = new SearchFragment();
        this.recentSearchFragment = new RecentSearchFragment();
        this.homeFragment = new HomeFragment();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainSearchBinding activityMainSearchBinding = DataBindingUtil.setContentView(this,R.layout.activity_main_search);

        initUser();
        initViewHeader();

        this.farmacoService = FarmacoService.getInstance(getCurrentUser(), getApplicationContext());

        initSearchViewBar();

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        this.manager = getSupportFragmentManager();

        if (savedInstanceState == null){
            this.homeFragment = new HomeFragment();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.search_container, homeFragment);
            transaction.commit();
        }
        getDeviceLocation();

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                doSerarch(query);
                saveThisSearch(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                //do something
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //loadRecentSearches();
                //openRecentSearchesTab();
            }

            @Override
            public void onSearchViewClosed() {
                //tabLayout.getTabAt(0).select();
            }
        });
    }

    private void initUser() {
        setCurrentUser(new User());
        getCurrentUser().setId(9);
        getCurrentUser().setUserName("ekumi");
        //getCurrentUser().setPassword("AvNcz4e9HNqW6xvE");
        getCurrentUser().setPassword("61a7211923240f7fd9dc4301be48532c");
    }

    private boolean getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        return mLocationPermissionGranted;
    }




    private void getDeviceLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                getLocationPermission();
                if (!mLocationPermissionGranted) return;
            }
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        try {
            this.latitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
            this.longitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), getString(R.string.no_gps_location), Toast.LENGTH_LONG).show();
            this.latitude = -1.0;
            this.longitude = -1.0;
        }
    }

    private void initViewHeader() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/
    }

    private void openRecentSearchesTab() {
        tabLayout.getTabAt(1).select();
    }

    private void saveThisSearch(String query) {
        //RecentSearch recentSearch = new RecentSearch(query, Calendar.getInstance().getTime(), getCurrentUser());
        //getRecentRearhDao().create(recentSearch);
    }

    private void loadRecentSearches() {
        recentSearchFragment.loadUserRecents(getCurrentUser());
    }

    private void initSearchViewBar() {
        searchView = findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        searchView.setEllipsize(true);
        //searchView.setSuggestionIcon();
        //searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(searchFragment, getString(R.string.medicine_list));
        //viewPagerAdapter.addFragment(recentSearchFragment, "Recent");
        //viewPagerAdapter.addFragment(new SavedSearch(), "Saved");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void doSerarch(String query) {

        if (this.latitude < 1){
            getDeviceLocation();
        }

        showLoading(SearchActivity.this, null, getString(R.string.searching));
        this.searchbleList.clear();

        setSearchQuery(query.trim());
        if (Utilities.isNetworkAvailable(SearchActivity.this)){
            doWebSearch();
        }else {
            hideLoading();
            Utilities.displayAlertDialog(SearchActivity.this, getString(R.string.network_not_available));
            //doLocalSearch();
        }
    }

    private void doLocalSearch() {

    }

    /*private void doWebSearch(String searchQuery) {
        Farmaco farmaco = new Farmaco();
        farmaco.setDesignacao(searchQuery);

        this.farmacoService.search(farmaco, this.latitude, this.longitude, getSearchRange(), 1);

        this.searchbleList = this.farmacoService.getWebService().getSearchResult().getCurrentSearchedRecords();

        displaySearchResults();
    }*/




    public String getSearchRange() {
        return "5";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void startSearch(@Nullable String initialQuery, boolean selectInitialQuery, @Nullable Bundle appSearchData, boolean globalSearch) {
        super.startSearch(initialQuery, selectInitialQuery, appSearchData, globalSearch);
    }



    private void displaySearchResults() {
        if (Utilities.listHasElements(this.searchbleList)){

            searchFragment = new SearchFragment();
            replaceFragment(searchFragment);
        }else {
            Utilities.displayAlertDialog(SearchActivity.this, getString(R.string.no_search_results));

        }
    }



    public String getSearchQuery() {
        return searchQuery;
    }

    public boolean isSearchFinished(){
        return this.isFarmacoSearchDone();
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public boolean isFarmaciaSearchDone() {
        return farmaciaSearchDone;
    }

    public void setFarmaciaSearchDone(boolean farmaciaSearchDone) {
        this.farmaciaSearchDone = farmaciaSearchDone;
    }

    public boolean isServicoSearchDone() {
        return servicoSearchDone;
    }

    public void setServicoSearchDone(boolean servicoSearchDone) {
        this.servicoSearchDone = servicoSearchDone;
    }

    public boolean isFarmacoSearchDone() {
        return farmacoSearchDone;
    }

    public void setFarmacoSearchDone(boolean farmacoSearchDone) {
        this.farmacoSearchDone = farmacoSearchDone;
    }

    public List<Searchble> getSearchbleList() {
        return this.searchbleList;
    }

    public void setSearchbleList(List<Searchble> searchbleList) {
        this.searchbleList = searchbleList;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction  = manager.beginTransaction();
        transaction .replace(R.id.search_container, fragment);
        transaction .addToBackStack(null);
        transaction .commit();
    }

    private void doWebSearch() {
        searchThread = new Thread(this);
        searchThread.start();
    }

    @Override
    public void run() {
        searchFarmacos();

        try {

            while (isRunning()){
                Thread.sleep(3000);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displaySearchResults();
                }
            });

            hideLoading();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public FarmacoService getFarmacoService() {
        return farmacoService;
    }

    public void searchFarmacos() {
        Uri.Builder uri =  getService().initServiceUri().appendPath(Farmaco.TABLE_NAME_FARMACO)
                            .appendPath(MobicareSyncService.SERVICE_SEARCH).appendPath(this.searchQuery).appendPath(getSearchRange())
                            .appendPath(String.valueOf(latitude)).appendPath(String.valueOf(longitude));

        final String url = uri.build().toString();

        serviceStatus = STATUS_RUNNING;

        searchbleList.clear();

        this.service.makeJsonArrayRequest(Request.Method.GET, url, null, getCurrentUser(), new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                errorMsg = error.getErrorMessage();
                serviceStatus = STATUS_FINISHED_WITH_ERROR;
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) {
            }

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {
                for(int i=0;i<response.length();i++){

                    try {
                        if (response.getJSONObject(i).has("message")){
                            resourceStatus = RESOURCE_STATUS_NOT_AVAILABLE;
                        }else {
                            searchbleList.add(utilities.fromJsonObject(response.getJSONObject(i), Farmaco.class));
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

    public int getCurrentSearchPage() {
        return this.currentSearchPage;
    }

    public int getNextSearchPage() {
        this.currentSearchPage += 1;
        return this.currentSearchPage;
    }

    public void setCurrentSearchPage(int currentSearchPage) {
        this.currentSearchPage = currentSearchPage;
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

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
    }
}

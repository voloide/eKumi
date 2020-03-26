package mz.co.insystems.mobicare.activities.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.google.android.material.tabs.TabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.base.BaseActivity;
import mz.co.insystems.mobicare.model.farmacia.Farmacia;
import mz.co.insystems.mobicare.model.farmaco.Farmaco;
import mz.co.insystems.mobicare.model.search.RecentSearch;
import mz.co.insystems.mobicare.model.search.Searchble;
import mz.co.insystems.mobicare.model.servico.Servico;
import mz.co.insystems.mobicare.model.user.User;
import mz.co.insystems.mobicare.sync.MobicareSyncService;
import mz.co.insystems.mobicare.sync.SyncError;
import mz.co.insystems.mobicare.sync.VolleyResponseListener;
import mz.co.insystems.mobicare.util.Utilities;

public class SearchActivity extends BaseActivity implements Runnable {

    private MaterialSearchView searchView;
    private Thread searchThread;
    private String searchQuery;
    private boolean farmaciaSearchDone;
    private boolean servicoSearchDone;
    private boolean farmacoSearchDone;
    private List<Searchble> searchbleList;


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private SearchFragment searchFragment;
    private RecentSearchFragment recentSearchFragment;

    public SearchActivity() {
        this.searchbleList = new ArrayList<>();
        this.searchFragment = new SearchFragment();
        this.recentSearchFragment = new RecentSearchFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        setCurrentUser((User) getIntent().getSerializableExtra(User.TABLE_NAME));

        initViewHeader();

        initSearchViewBar();
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
                openRecentSearchesTab();
            }

            @Override
            public void onSearchViewClosed() {
                tabLayout.getTabAt(0).select();
            }
        });
    }

    private void initViewHeader() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void openRecentSearchesTab() {
        tabLayout.getTabAt(1).select();
    }

    private void saveThisSearch(String query) {
        RecentSearch recentSearch = new RecentSearch(query, Calendar.getInstance().getTime(), getCurrentUser());
        try {
            getRecentRearhDao().create(recentSearch);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        viewPagerAdapter.addFragment(searchFragment, "Search");
        viewPagerAdapter.addFragment(recentSearchFragment, "Recent");
        viewPagerAdapter.addFragment(new SavedSearch(), "Saved");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void doSerarch(String query) {
        showLoading(SearchActivity.this, null, getString(R.string.searching));
        this.searchbleList.clear();

        setSearchQuery(query.trim());
        if (Utilities.isNetworkAvailable(SearchActivity.this)){
            doWebSearch();
        }else {
            doLocalSearch();
        }
    }

    private void doLocalSearch() {

    }

    private void doWebSearch() {
        searchThread = new Thread(this);
        searchThread.start();
    }


    private void searchFarmaco(Uri.Builder uri) {
        uri.appendPath(Farmaco.TABLE_NAME_FARMACO);
        uri.appendPath(MobicareSyncService.SERVICE_SEARCH);
        uri.appendPath(this.searchQuery);
        final String url = uri.build().toString();

        getService().makeJsonArrayRequest(Request.Method.GET, url, null, getCurrentUser(), new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                setFarmacoSearchDone(true);
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) { }

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {
                for(int i=0;i<response.length();i++){

                    try {
                        if (response.getJSONObject(i).has("message")){

                        }else {
                            searchbleList.add(new Farmaco().fromJsonObject(response.getJSONObject(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                setFarmacoSearchDone(true);
            }
        });
    }

    private void searchServico(Uri.Builder uri) {
        uri.appendPath(Servico.TABLE_NAME_SERVICO);
        uri.appendPath(MobicareSyncService.SERVICE_SEARCH);
        uri.appendPath(this.searchQuery);
        final String url = uri.build().toString();

        getService().makeJsonArrayRequest(Request.Method.GET, url, null, getCurrentUser(), new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                setServicoSearchDone(true);
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) { }

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {
                for(int i=0;i<response.length();i++){

                    try {
                        if (response.getJSONObject(i).has("message")){

                        }else {
                            searchbleList.add(new Servico().fromJsonObject(response.getJSONObject(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                setServicoSearchDone(true);
            }
        });
    }

    private void searchFarmacia(Uri.Builder uri) {
        uri.appendPath(Farmacia.TABLE_NAME_FARMACIA);
        uri.appendPath(MobicareSyncService.SERVICE_SEARCH);
        uri.appendPath(this.searchQuery);
        final String url = uri.build().toString();

        getService().makeJsonArrayRequest(Request.Method.GET, url, null, getCurrentUser(), new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                setFarmaciaSearchDone(true);
            }

            @Override
            public void onResponse(JSONObject response, int myStatusCode) { }

            @Override
            public void onResponse(JSONArray response, int myStatusCode) {
                for(int i=0;i<response.length();i++){

                    try {
                        if (response.getJSONObject(i).has("message")){

                        }else {
                            searchbleList.add(new Farmacia().fromJsonObject(response.getJSONObject(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                setFarmaciaSearchDone(true);
            }
        });
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
    public boolean noSyncError() {
        return false;
    }

    @Override
    public boolean syncOperationDone() {
        return false;
    }

    @Override
    public void run() {

        //Implement Limits on data loading
        searchFarmacia(service.initServiceUri());
        searchFarmaco(service.initServiceUri());
        searchServico(service.initServiceUri());

        try {
            searchThread.join(2000);
            if (!this.isSearchFinished()) searchThread.join(2000);
            if (!this.isSearchFinished()) searchThread.join(2000);

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

    private void displaySearchResults() {
        if (Utilities.listHasElements(this.searchbleList)){
            searchFragment.notify();
        }else {
            Utilities.displayCommonAlertDialog(getApplicationContext(), getString(R.string.no_search_results));
        }
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public boolean isSearchFinished(){
        return this.isFarmaciaSearchDone() && this.isFarmacoSearchDone() && this.isServicoSearchDone();
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
        return searchbleList;
    }

    public void setSearchbleList(List<Searchble> searchbleList) {
        this.searchbleList = searchbleList;
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
}

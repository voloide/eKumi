package mz.co.insystems.mobicare.activities.farmacia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.android.volley.Request;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.activities.search.SearchActivity;
import mz.co.insystems.mobicare.base.BaseActivity;
import mz.co.insystems.mobicare.databinding.ActivityFarmaciaBinding;
import mz.co.insystems.mobicare.model.farmacia.Farmacia;
import mz.co.insystems.mobicare.model.farmaco.Farmaco;
import mz.co.insystems.mobicare.model.user.User;
import mz.co.insystems.mobicare.sync.MobicareSyncService;
import mz.co.insystems.mobicare.sync.SyncError;
import mz.co.insystems.mobicare.sync.VolleyResponseListener;

public class FarmaciaActivity extends BaseActivity implements Runnable{

    private Toolbar toolbar;
    private Farmacia currentFarmacia;

    private Farmaco selectedFarmaco;
    private Thread searchThread;
    private long farmacoId;
    private RecyclerView recyclerView;
    private FarmacoAdapter farmacoAdapter;
    private SyncError requestError;
    private long farmaciaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFarmaciaBinding activityFarmaciaBinding = DataBindingUtil.setContentView(this, R.layout.activity_farmacia);

        setCurrentUser((User) getIntent().getSerializableExtra(User.TABLE_NAME));

        this.farmacoId = getIntent().getLongExtra(Farmaco.COLUMN_FARMACO_ID, 0);

        this.farmaciaId = getIntent().getLongExtra(Farmacia.COLUMN_FARMACIA_ID, 0);

        initViewHeader();

        doWebSearch();


        recyclerView = activityFarmaciaBinding.farmacoList;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    private void initViewHeader() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void getFarmaco(Uri.Builder uri) {
        uri.appendPath(Farmaco.TABLE_NAME_FARMACO);
        uri.appendPath("getById");
        uri.appendPath(String.valueOf(farmacoId));
        uri.appendPath(String.valueOf(farmaciaId));

        final String url = uri.build().toString();


        getService().makeJsonObjectRequest(Request.Method.GET, url, null, getCurrentUser(), new VolleyResponseListener() {
            @Override
            public void onError(SyncError error) {
                requestError = error;
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

    private void doWebSearch() {
        searchThread = new Thread(this);
        searchThread.start();
    }

    private  void displaySearchResults(){
        currentFarmacia = selectedFarmaco.getFarmacia();
        currentFarmacia.setFarmacos(new ArrayList<>());
        currentFarmacia.getFarmacos().add(selectedFarmaco);

        //farmacoAdapter.setFarmacos(currentFarmacia.getFarmacos());
        farmacoAdapter = new FarmacoAdapter(this, currentFarmacia.getFarmacos());
        recyclerView.setAdapter(farmacoAdapter);
        farmacoAdapter.notifyDataSetChanged();
    }

    @Override
    public void run() {

        getFarmaco(service.initServiceUri());

        try {

            while (selectedFarmaco == null && requestError == null){
                Thread.sleep(2000);
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

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

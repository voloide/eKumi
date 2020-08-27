package mz.co.insystems.service.ekumi.activities.maps;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.databinding.ActivityFarmaciaMapsBinding;
import mz.co.insystems.service.ekumi.model.endereco.Endereco;
import mz.co.insystems.service.ekumi.model.farmacia.Farmacia;
import mz.co.insystems.service.ekumi.model.farmaco.Farmaco;
import mz.co.insystems.service.ekumi.model.user.User;
import mz.co.insystems.service.ekumi.util.Utilities;

public class FarmaciaMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private Farmacia farmacia;
    private Farmaco farmaco;
    private User user;
    private Utilities utilities;
    private double deviceLat;
    private double deviceLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityFarmaciaMapsBinding mapsBinding = DataBindingUtil.setContentView(this, R.layout.activity_farmacia_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        utilities = Utilities.getInstance();

        Bundle extras = this.getIntent().getExtras();
        String farmaciaJson = extras.getString(Farmacia.TABLE_NAME_FARMACIA);
        String farmacoJson = extras.getString(Farmaco.TABLE_NAME_FARMACO);
        String userJson = extras.getString(User.TABLE_NAME);

        deviceLat = extras.getDouble(Endereco.COLUMN_ENDERECO_LATITUDE);
        deviceLog = extras.getDouble(Endereco.COLUMN_ENDERECO_LONGITUDE);

        if (Utilities.stringHasValue(farmaciaJson)){
            try {
                farmacia = utilities.fromJson(farmaciaJson, Farmacia.class);
                farmaco = utilities.fromJson(farmacoJson, Farmaco.class);
                user = utilities.fromJson(userJson, User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mapsBinding.setFarmacia(this.farmacia);
        }
    }

    private Farmaco initFarmaco() {
        return new Farmaco(getIntent().getLongExtra(Farmaco.COLUMN_FARMACO_ID, 0));
    }

    private Farmacia initFarmacia() {
        return new Farmacia(getIntent().getIntExtra(Farmacia.COLUMN_FARMACIA_ID, 0));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);

        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng farmaciaLatLng = new LatLng(this.farmacia.getEndereco().getLatitude(), this.farmacia.getEndereco().getLongitude());
        LatLng devideLatLng = new LatLng(this.deviceLat, this.deviceLog);

        mMap.addMarker(new MarkerOptions().position(farmaciaLatLng).title(this.farmacia.getNome()).snippet(this.farmacia.getEndereco().getRuaAvenida()));
        //mMap.addMarker(new MarkerOptions().position(devideLatLng).title(getString(R.string.device_location)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(farmaciaLatLng, 12));

        mMap.setOnMarkerClickListener(this);
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public Farmaco getFarmaco() {
        return farmaco;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}

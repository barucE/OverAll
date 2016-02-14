package a.buitress.overall;

import android.app.FragmentTransaction;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class pantallamapa extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

   GoogleMap mapa;
    private GoogleApiClient _googleApiClient;
    private Location location;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallamapa);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapa);
        mapa = mapFragment.getMap();
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.setMyLocationEnabled(true);

        mapa.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(20.659698, -103.349609)));
        mapa.moveCamera(CameraUpdateFactory.zoomTo(14));

        mapFragment.getMapAsync(this);

        buildApiClient();
    }

    private void buildApiClient() {
        _googleApiClient = new
                GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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
    public void onMapReady(GoogleMap map) {
        _googleApiClient.connect();
        _googleApiClient.disconnect();
    }

    private void updateMarker(Location loc) {
        if(marker == null) {
            marker = mapa.addMarker(new MarkerOptions()
                    .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .title("Aquí"));
        }
        else {
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }

    }


    @Override
    public void onConnected(Bundle bundle) {
        location = LocationServices.FusedLocationApi.getLastLocation(_googleApiClient);
        updateMarker(location);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (_googleApiClient.isConnected()) {
            _googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}


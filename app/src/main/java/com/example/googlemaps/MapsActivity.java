package com.example.googlemaps;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.googlemaps.ModelFlicker.ApiCallFlikr;
import com.example.googlemaps.ModelFlicker.FlickrApi;
import com.example.googlemaps.ModelFlicker.Photo;
import com.example.googlemaps.ModelFlicker.Photos;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemaps.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Photos photo;
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<String> secrets = new ArrayList<>();
    private ArrayList<String> servers = new ArrayList<>();
    private ArrayList<String> imageUrl = new ArrayList<>();
    private String baseImageUrl = "https://live.staticflickr.com/";
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (imageUrl != null) {
            imageUrl.clear();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Retrofit inicialization
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.flickr.com/services/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap map) {

                // When map is clicked
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Log.d("TAG", "onMapClick latitud and longitud: "+latLng);
                        // Creating a marker
                        MarkerOptions markerOptions = new MarkerOptions();

                        // Setting the position for the marker
                        markerOptions.position(latLng);

                        // Setting the title for the marker.
                        // This will be displayed on taping the marker
                        markerOptions.title(getAddress(latLng.latitude, latLng.longitude));

                        // Clears the previously touched position
                        mMap.clear();

                        // Animating to the touched position
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                        // Placing a marker on the touched position
                        mMap.addMarker(markerOptions);

                        ApiCallFlikr apiCall = retrofit.create(ApiCallFlikr.class);
                        Call<FlickrApi> call = apiCall.getData(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));

                        call.enqueue(new Callback<FlickrApi>(){
                            @Override
                            public void onResponse(Call<FlickrApi> call, Response<FlickrApi> response) {
                                if(response.code()!=200){
                                    Log.i("testApi", "checkConnection");
                                    return;
                                }

                                photo = response.body().getPhotos();
                                if (photo.getPhoto().size()!=0) {
                                    for (int i=0; i < 10; i++) {
                                        if (photo.getPhoto().get(i) != null) {
                                            photos.add(photo.getPhoto().get(i));
                                            ids.add(photos.get(i).getId());
                                            servers.add(photos.get(i).getServer());
                                            secrets.add(photos.get(i).getSecret());
                                            imageUrl.add(baseImageUrl + servers.get(i) +"/"+ ids.get(i) +"_"+ secrets.get(i)+".jpg");
                                        }
                                        Log.i("testApi", imageUrl.get(i).toString());
                                    }
                                }
                                if (imageUrl != null) {
                                    bundle.putSerializable("imageUrl", imageUrl);
                                    Intent sliderActivity = new Intent(MapsActivity.this, SliderActivity.class);
                                    sliderActivity.putExtras(bundle);
                                    startActivity(sliderActivity);
                                }
                            }

                            @Override
                            public void onFailure(Call<FlickrApi> call, Throwable t) {

                            }
                        });

                        ApiThread apiT = new ApiThread(latLng.latitude, latLng.longitude);
                        apiT.execute();
                    }
                });
            }
        });
    }

    public String getAddress(double lat, double lng) {
        try {
            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(lat, lng, 1);
            if (addresses.isEmpty()) {
                Toast.makeText(this, "No s’ha trobat informació", Toast.LENGTH_LONG).show();
            } else {
                if (addresses.size() > 0) {
                    String msg =addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();

                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    return msg;
                }
            }
        }
        catch(Exception e){
            Toast.makeText(this, "No Location Name Found", Toast.LENGTH_LONG).show();
        }
        return "No Location Name Found";
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
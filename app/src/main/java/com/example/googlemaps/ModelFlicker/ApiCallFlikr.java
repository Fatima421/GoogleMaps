package com.example.googlemaps.ModelFlicker;

import com.example.googlemaps.Model.ModelApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCallFlikr {
    @GET("?method=flickr.photos.search&api_key=79d466885188b99d6762980d64029892&format=json&nojsoncallback=1")
    Call<FlickrApi> getData(@Query("lat") String lat, @Query("lon") String lng);
}

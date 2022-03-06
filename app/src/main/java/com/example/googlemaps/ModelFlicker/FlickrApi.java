package com.example.googlemaps.ModelFlicker;

public class FlickrApi {
    Photos photos;
    String stat;

    public FlickrApi() {

    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhoto(Photos photo) {
        this.photos = photo;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}

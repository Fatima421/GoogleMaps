package com.example.googlemaps.Model;

public class ModelApi {
    public String status;
    public ModelResults results;

    public ModelApi() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ModelResults getResults() {
        return results;
    }

    public void setResults(ModelResults results) {
        this.results = results;
    }
}
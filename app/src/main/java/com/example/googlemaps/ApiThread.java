package com.example.googlemaps;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiThread extends AsyncTask<Void, Void, String> {
    double lat;
    double lng;

    public ApiThread(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.sunrise-sunset.org/json?lat=" + lat + "&lng="+lng);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // Read API results
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String data = bufferedReader.readLine();
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(data);
            jObject = jObject.getJSONObject("results");
            String sunrise = jObject.getString("sunrise");
            String sunset = jObject.getString("sunset");
            Log.i("sunrise", "------>" + sunrise);
            Log.i("sunset", "------>" + sunset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

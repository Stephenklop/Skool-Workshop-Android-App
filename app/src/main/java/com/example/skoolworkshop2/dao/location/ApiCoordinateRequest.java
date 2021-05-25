package com.example.skoolworkshop2.dao.location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class ApiCoordinateRequest implements CoordinateRequest {
    private HttpsURLConnection connection;
    private final String BASE_URL = "https://nominatim.openstreetmap.org/search?";
    private final String SEARCH_POSTALCODE = "postalcode=";
    private final String FORMAT = "&format=json";

    @Override
    public double[] getCoordinates(String postalCode) {
        double[] coordinates;
        try{
            connect(BASE_URL + SEARCH_POSTALCODE + postalCode + FORMAT);
            connection.setRequestMethod("GET");

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String data = input.readLine();
            while(data != null){
                JSONArray resultList = new JSONArray(data);
                if(resultList != null){
                    JSONObject resultObject = resultList.getJSONObject(0);
                    double lat = resultObject.getDouble("lat");
                    double lon = resultObject.getDouble("lon");
                    System.out.println(lat + "   " + lon);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        return new double[0];
    }

    private void connect(String url) throws Exception{
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }
}

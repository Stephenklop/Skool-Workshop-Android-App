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

    private final String BASE_URL_DISTANCE = "https://router.project-osrm.org/route/v1/driving/4.766496028722843,51.59854262243952;";

    @Override
    public double[] getCoordinates(String postalCode) {
        double[] coordinates = new double[2];
        try{
            connect(BASE_URL + SEARCH_POSTALCODE + postalCode + FORMAT);
            connection.setRequestMethod("GET");

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String data = input.readLine();
            if(data != null){
                JSONArray resultList = new JSONArray(data);
                if(resultList != null){
                    JSONObject resultObject = resultList.getJSONObject(0);
                    double lat = resultObject.getDouble("lat");
                    double lon = resultObject.getDouble("lon");
                    System.out.println(lat + "   " + lon);
                    coordinates[0] = lon;
                    coordinates[1] = lat;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        return coordinates;
    }

    @Override
    public double getDistance(double[] coordinatesPostalCode) {
        double distance = -1;
        try{
            connect(BASE_URL_DISTANCE + coordinatesPostalCode[0] + "," + coordinatesPostalCode[1]);
            connection.setRequestMethod("GET");

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String data = input.readLine();
            if(data != null){
                JSONObject resultList = new JSONObject(data);
                if(resultList != null){
                    JSONArray result = resultList.getJSONArray("routes");
                    JSONObject resultObject = result.getJSONObject(0);
                    distance = resultObject.getDouble("distance");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return distance / 1000;
    }

    private void connect(String url) throws Exception{
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }
}

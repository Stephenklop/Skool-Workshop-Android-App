package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import com.example.skoolworkshop2.dao.ProductDAO;
import com.example.skoolworkshop2.domain.Workshop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class APIProductDAO implements ProductDAO {
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api/";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    @Override
    public List<Workshop> getAllProducts() {
        final String PATH = "product";
        List<Workshop> result = new ArrayList<>();

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject response = new JSONObject(inputLine);
                JSONArray workshops = response.getJSONArray("result");

                for (int i = 0; i < workshops.length(); i++) {
                    JSONObject jsonObject = (JSONObject) workshops.get(i);
                    result.add(parseWorkshop(jsonObject));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Workshop> getAllProductsByCategory(int id) {
        final String PATH = "product?category=" + id;
        List<Workshop> result = new ArrayList<>();

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject response = new JSONObject(inputLine);
                JSONArray workshops = response.getJSONArray("result");

                for (int i = 0; i < workshops.length(); i++) {
                    JSONObject jsonObject = (JSONObject) workshops.get(i);
                    result.add(parseWorkshop(jsonObject));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Workshop getProduct(int id) {
        final String PATH = "product/" + id;
        Workshop result = null;

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject response = new JSONObject(inputLine);
                JSONObject workshop = response.getJSONObject("result");
                result = parseWorkshop(workshop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Workshop parseWorkshop(JSONObject jsonObject) {
        Workshop result = null;

        try {
            result = new Workshop(jsonObject.getDouble("id"), jsonObject.getString("name").replace("Workshop ", ""), jsonObject.getString("permalink"), jsonObject.getString("short_description"), jsonObject.getString("description"), jsonObject.getJSONObject("image").getString("src"), jsonObject.getJSONObject("image").getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
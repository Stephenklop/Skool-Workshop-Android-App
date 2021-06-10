package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import com.example.skoolworkshop2.dao.ProductDAO;
import com.example.skoolworkshop2.domain.Product;

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
    public List<Product> getAllProducts() {
        final String PATH = "product";
        List<Product> result = new ArrayList<>();

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
                    result.add(parseProduct(jsonObject));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Product> getAllProductsByCategory(int id) {
        final String PATH = "product?category=" + id;
        List<Product> result = new ArrayList<>();

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
                    result.add(parseProduct(jsonObject));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Product getProduct(int id) {
        final String PATH = "product/" + id;
        Product result = null;

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject response = new JSONObject(inputLine);
                JSONObject workshop = response.getJSONObject("result");
                result = parseProduct(workshop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Product parseProduct(JSONObject jsonObject) {
        Product result = null;

//        int id = 999999;
        String name = "";
        String productType = "";
        String category = "";
        String permaLink = "";
        String type = "";
        String status = "";
        String description = "";
        String shortDescription = "";
        String buildupDescription = "";
        String practicalInformation = "";
        String costsInfo = "";
        String image = "";
        String imageName = "";
        String video = "";

//        try {
//            id = jsonObject.getInt("id");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        try {
            name = jsonObject.getString("name").replace("Workshop ", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray categories = jsonObject.getJSONArray("categories");
            for (int i = 0; i < categories.length(); i++) {
                if (categories.getJSONObject(i).getString("name").toLowerCase().contains("workshop") || categories.getJSONObject(i).getString("name").toLowerCase().contains("cultuurdag")) {
                    productType = categories.getJSONObject(i).getString("name");
                } else {
                    category = categories.getJSONObject(i).getString("name");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            permaLink = jsonObject.getString("permalink");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            description = jsonObject.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            buildupDescription = jsonObject.getString("buildup_workshop");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            type = jsonObject.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            status = jsonObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            shortDescription = jsonObject.getString("short_description");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            practicalInformation = jsonObject.getString("practical_information");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            costsInfo = jsonObject.getString("costs_info");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            image = jsonObject.getJSONObject("image").getString("src");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            imageName = jsonObject.getJSONObject("image").getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            video = jsonObject.getString("video");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            result = new Product(
                    jsonObject.getInt("id"),
                    name,
                    productType,
                    category,
                    permaLink,
                    type,
                    status,
                    description,
                    shortDescription,
                    buildupDescription,
                    practicalInformation,
                    costsInfo,
                    image,
                    imageName,
                    video
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
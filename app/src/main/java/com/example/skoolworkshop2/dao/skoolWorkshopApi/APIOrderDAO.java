package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import com.example.skoolworkshop2.dao.OrderDAO;
import com.example.skoolworkshop2.domain.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class APIOrderDAO implements OrderDAO {

    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    @Override
    public List<Order> getAllOrdersFromUser(int userId) {
        List<Order> orders = new ArrayList<>();
        final String PATH = "/order/" + userId;

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject input = new JSONObject(inputLine);
                JSONArray array = input.getJSONArray("booking_info");

                JSONObject booking = array.getJSONObject(0);
                String date = booking.getString("start_date");

                JSONArray lineItems = input.getJSONArray("line_items");
                String status = input.getString("status");


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order parseOrder(JSONObject object) throws JSONException {
        Order order = null;

        String status = object.getString("status");
        int costumerId = object.getInt("costumer_id");
    }
}

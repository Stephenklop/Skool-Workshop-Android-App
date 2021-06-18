package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import androidx.room.FtsOptions;

import com.example.skoolworkshop2.dao.OrderDAO;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.ShippingAddress;

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
                JSONArray input = new JSONArray(inputLine);
                for(int i = 0; i < input.length(); i++){
                    orders.add(parseOrder(input.getJSONObject(i)));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order parseOrder(JSONObject object) throws JSONException {
        Order order = null;
        BillingAddress address = (BillingAddress) object.get("billing");
        ShippingAddress shippingAddress = (ShippingAddress) object.get("shipping");

        try{
            String status = object.getString("status");
            int costumerId = object.getInt("costumer_id");
            int billingId = address.getId();
            int shippingId = shippingAddress.getId();
            String paymentMethod = object.getString("payment_method");
            String paymentMethodTitle = object.getString("payment_method_title");
            String costumerNote = object.getString("customer_note");
            int billingCJP = object.getInt("billing_CJP");
            String billingVideo = object.getString("billing_video");
            String reservationSystem = object.getString("reservation_system");
            JSONArray shippingLines = object.getJSONArray("shipping_lines");
            JSONObject shippingObject = shippingLines.getJSONObject(0);
            Double distance = shippingObject.getDouble("distance");
            Double price = shippingObject.getDouble("price");
            order = new Order(status, costumerId, billingId, shippingId, paymentMethod, paymentMethodTitle, costumerNote, billingCJP, billingVideo, reservationSystem, distance, price);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return order;

    }
}

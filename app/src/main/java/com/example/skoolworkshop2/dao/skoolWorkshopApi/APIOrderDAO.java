package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import android.util.Log;

import androidx.room.FtsOptions;

import com.example.skoolworkshop2.dao.OrderDAO;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.Reservation;
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

    private final String TAG = getClass().getSimpleName();
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    @Override
    public List<Reservation> getAllReservationsFromUser(int userId) {
        List<Reservation> orders = new ArrayList<>();
        final String PATH = "/order/" + userId;

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONArray input = new JSONArray(inputLine);
                for(int i = 0; i < input.length(); i++){
                    orders.add(parseReservation(input.getJSONObject(i)));
                    Log.d(TAG, "order: " + parseReservation(input.getJSONObject(i)));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getAllReservationsFromUser: " + orders.size());
        return orders;
    }

    public Reservation parseReservation(JSONObject object) throws JSONException {
        APIUserDAO dao = new APIUserDAO();

        Reservation order = null;
        try{
            int id = object.getInt("id");
            String status = object.getString("status");
            int costumerId = object.getInt("customer_id");
            String date = object.getString("date_created");

            order = new Reservation(id, status, date, costumerId, "");
        } catch (JSONException e){
            e.printStackTrace();
        }

        return order;

    }

}

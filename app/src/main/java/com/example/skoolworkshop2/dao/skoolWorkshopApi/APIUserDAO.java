package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.skoolworkshop2.dao.UserDAO;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.ShippingAddress;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIUserDAO implements UserDAO {
    private final String TAG = getClass().getSimpleName();
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api/";
    private HttpURLConnection connection;
    private Customer lastCustomer;
    private BillingAddress billingAddress;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpURLConnection) connectionUrl.openConnection();
    }

    public JSONObject parseBilling(BillingAddress billingAddress) throws JSONException {
        Gson g = new Gson();

        JSONObject jsonObject = new JSONObject(g.toJson(billingAddress));
        return jsonObject;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public User signUserIn(String username, String password) {
        final String PATH = "account/login";
        User result = null;

        try {
            connect(BASE_URL + PATH);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setConnectTimeout(1000000);


            String jsonInput = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
            System.out.println("JSON STRING: " + jsonInput);

            OutputStream os = connection.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();

            System.out.println(connection.getRequestMethod());
            System.out.println(connection.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("RESPONSE: " + inputLine);
                JSONObject response = new JSONObject(inputLine);
                JSONObject user = response.getJSONObject("result");

                JSONArray metaData = user.getJSONArray("meta_data");
                int points = 0;
                for(int i = 0; i < metaData.length(); i++){
                    JSONObject object = metaData.getJSONObject(i);
                    if ( object.get("key").equals("_ywpar_user_total_points")){
                        points = Integer.parseInt(object.get("value").toString());
                        Log.d("POINTS", points + "");
                    }
                }
                String email = user.getString("email");
                int id = Integer.parseInt(user.get("id").toString());
                String userName = user.get("username").toString();
                
                Log.d("POINTS", points + "");
                Log.d(TAG, "signUserIn: billingaddress: " + billingAddress.toString());

                result = new User(id, email, userName, points, 0, 0);

                String firstname = user.getString("first_name");
                String lastName = user.getString("last_name");
                JSONObject adress = user.getJSONObject("billing");
                String adress_1 = adress.getString("address_1");
                String street = "";
                String number = "";
                String streetAndNumber[] = adress_1.split(" ");
                if(streetAndNumber.length > 1){
                    street = streetAndNumber[0];
                    number = streetAndNumber[1];
                }
                String postCode = adress.getString("postcode");
                String city = adress.getString("city");
                String state = adress.getString("state");
                String country = adress.getString("country");



                Customer customer = new Customer(id, firstname, lastName, email, street, number, postCode, city, state, country);
                lastCustomer = customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Customer getLastCustomer() {
        return lastCustomer;
    }

    @Override
    public User registerUser(String username, String email, String password) {
        final String PATH = "account/register";
        User result = null;

        try{
            connect(BASE_URL + PATH);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonInput = "{\"username\": \"" + username + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
            System.out.println("JSON STRING: " + jsonInput);

            System.out.println("PROPERTIES: " + connection.getRequestProperties());

            OutputStream os = connection.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();

            System.out.println(connection.getRequestMethod());
            System.out.println(connection.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                System.out.println("RESPONSE: " + inputLine);
                JSONObject response = new JSONObject(inputLine);
                JSONObject user = response.getJSONObject("result");

                username = user.get("username").toString();
                email = user.get("email").toString();
                int id = user.getInt("id");


                int points = 0;
                JSONArray metaData = user.getJSONArray("meta_data");
                for(int i = 0; i < metaData.length(); i++){
                    JSONObject object = metaData.getJSONObject(i);
                    if ( object.get("key").equals("_ywpar_user_total_points")){
                        points = Integer.parseInt(object.get("value").toString());
                        Log.d("POINTS", points + "");
                    }
                }

                result = new User(id, email, username, points, 0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

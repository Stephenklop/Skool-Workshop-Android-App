package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import android.util.Log;

import com.example.skoolworkshop2.dao.UserDAO;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIUserDAO implements UserDAO {
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api/";
    private HttpURLConnection connection;
    private Customer lastCustomer;
    private User lastUser;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpURLConnection) connectionUrl.openConnection();
    }

    @Override
    public User signUserIn(String username, String password) {
        final String PATH = "account/login";
        User result = null;

        try {
            connect(BASE_URL + PATH);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");


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
                result = new User(id, email, userName, points);



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

                result = new User(id, email, username, points);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Customer getCustomerInfo(int id){
        final String PATH = "customer/" + id;
        User result = null;
        Customer customer = null;

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");


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
                String userName = user.get("username").toString();

                Log.d("POINTS", points + "");
                result = new User(id, email, userName, points);



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



                customer = new Customer(id, firstname, lastName, email, street, number, postCode, city, state, country);
                lastUser = result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customer;
    }

    public User getLastUser(){
        return lastUser;
    }
}

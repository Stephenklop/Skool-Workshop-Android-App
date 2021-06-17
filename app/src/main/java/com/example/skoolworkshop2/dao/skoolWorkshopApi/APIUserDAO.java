package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.skoolworkshop2.dao.UserDAO;
import com.example.skoolworkshop2.domain.BillingAddress;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.ShippingAddress;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.ui.SplashScreenActivity;


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
    private User lastUser;
    private Application application;


    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpURLConnection) connectionUrl.openConnection();
    }

    public APIUserDAO(){
        this.application = SplashScreenActivity.application;
    }

    public BillingAddress parseJSONToBillling(JSONObject jsonObject){
        BillingAddress billingAddress = null;

        String firstName = "";
        String lastName = "";
        String company = "";
        String postcode = "";
        String city = "";
        String address = "";
        String country = "";
        String phone = "";
        String email = "";


        try {
            firstName = jsonObject.getString("first_name");
            lastName = jsonObject.getString("last_name");
            company = jsonObject.getString("company");
            postcode = jsonObject.getString("company");
            city = jsonObject.getString("city");
            address = jsonObject.getString("address_1");
            country = jsonObject.getString("country");
            phone = jsonObject.getString("phone");
            email = jsonObject.getString("email");




        } catch (JSONException e) {
            e.printStackTrace();
        }

        billingAddress = new BillingAddress(firstName, lastName, company, postcode, city, address, country, phone, email);
        System.out.println(billingAddress);

        return billingAddress;
    }

    public ShippingAddress parseJSONToShipping(JSONObject jsonObject){
        ShippingAddress shippingAddress = null;

        String firstName = "";
        String lastName = "";
        String company = "";
        String postcode = "";
        String city = "";
        String address = "";
        String country = "";


        try {
            firstName = jsonObject.getString("first_name");
            lastName = jsonObject.getString("last_name");
            company = jsonObject.getString("company");
            postcode = jsonObject.getString("company");
            city = jsonObject.getString("city");
            address = jsonObject.getString("address_1");
            country = jsonObject.getString("country");




        } catch (JSONException e) {
            e.printStackTrace();
        }

        shippingAddress = new ShippingAddress(firstName, lastName, company, postcode, city, address, country);
        System.out.println(shippingAddress);

        return shippingAddress;
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
                BillingAddress billingAddress = parseJSONToBillling(user.getJSONObject("billing"));
                ShippingAddress shippingAddress = parseJSONToShipping(user.getJSONObject("shipping"));
                Log.d("POINTS", points + "");

                result = new User(id, email, userName, points, billingAddress.getId(), shippingAddress.getId());
              
                Customer customer = parseCustomer(user);
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
    public void updateUser(String email, String displayName, String firstName, String lastName) {
//        LocalAppStorage localAppStorage = new LocalAppStorage(context);
        int id = LocalDb.getDatabase(application).getUserDAO().getInfo().getId();
        System.out.println("integer: " + id + "++++++++++++++++++++++++++++++++++++++++");

        final String PATH = "account/" + id;
        User result = null;

        try{
            connect(BASE_URL + PATH);
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");

            String jsonInput = "{\"email\": \"" + email + "\", \"name\": \"" + displayName + "\", \"first_name\": \"" + firstName + "\", \"last_name\": \"" + lastName + "\"}";
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
                try {
                    JSONObject userData = response.getJSONObject("result");
                    User user = parseUser(userData);
                    Customer customer = parseCustomer(userData);

                    LocalDb.getDatabase(application).getUserDAO().deleteInfo();
                    LocalDb.getDatabase(application).getUserDAO().insertInfo(user);
                    LocalDb.getDatabase(application).getCustomerDAO().deleteCustomer();
                    LocalDb.getDatabase(application).getCustomerDAO().addCustomer(customer);


                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Customer parseCustomer(JSONObject jsonObject){
        Customer result = null;

        int id;
        String firstName;
        String lastName;
        String email;
        String street = "";
        String houseNumber = "";
        String postCode;
        String city;
        String state;
        String country;

        try {
            email = jsonObject.getString("email");
            id = Integer.parseInt(jsonObject.get("id").toString());
            firstName = jsonObject.getString("first_name");
            lastName = jsonObject.getString("last_name");
            JSONObject adress = jsonObject.getJSONObject("billing");
            String adress_1 = adress.getString("address_1");
            //TODO what if spatie in straatnaam
            String streetAndNumber[] = adress_1.split(" ");
            if(streetAndNumber.length > 1){
                street = streetAndNumber[0];
                houseNumber = streetAndNumber[1];
            }
            postCode = adress.getString("postcode");
            city = adress.getString("city");
            state = adress.getString("state");
            country = adress.getString("country");

            result = new Customer(id, firstName, lastName, email, street, houseNumber, postCode, city, state, country);

        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public User parseUser(JSONObject user){
        User result = null;

        try{
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
            BillingAddress billingAddress = parseJSONToBillling(user.getJSONObject("billing"));
            ShippingAddress shippingAddress = parseJSONToShipping(user.getJSONObject("shipping"));
            Log.d("POINTS", points + "");

            result = new User(id, email, userName, points, billingAddress.getId(), shippingAddress.getId());
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
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

                result = parseUser(user);

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

package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.dao.UserDAO;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class APIUserDAO extends AppCompatActivity implements UserDAO {
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api/";
    private HttpURLConnection connection;
    private Customer lastCustomer;

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
    public void updateUser(String email, String displayName, String firstName, String lastName) {
//        LocalAppStorage localAppStorage = new LocalAppStorage(context);
        int id = LocalDb.getDatabase(getBaseContext()).getUserDAO().getInfo().getId();

        final String PATH = "account/" + id;
        User result = null;

        try{
            connect(BASE_URL + PATH);
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            //TODO add header with App token
            //... idk of dat hier of ergens anders moet ...
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");

            String jsonInput = "{\"email\": \"" + email + "\", \"name\": \"" + displayName + "\", \"first_name\": \"" + firstName + "\", \"last_name\": \"" + lastName + "\", }";
            System.out.println("JSON STRING: " + jsonInput);

            //TODO send actual request to de API
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
//                    JSONObject userData = response.getJSONObject("result");
//                    User user = parseUser(userData);
//                    Customer customer = parseCustomer(userData);
//
//                    LocalDb.getDatabase(getBaseContext()).getUserDAO().deleteInfo();
//                    LocalDb.getDatabase(getBaseContext()).getUserDAO().insertInfo(user);
//                    LocalDb.getDatabase(getBaseContext()).getCustomerDAO().deleteCustomer();
//                    LocalDb.getDatabase(getBaseContext()).getCustomerDAO().addCustomer(customer);


                } catch (Exception e){
                    e.printStackTrace();
                }

            }
            //TODO request to GET the 'new' information from the API
            //andere methodes

            //TODO if the 'new' information is new, delete old data from the localDB

            //TODO update database with the new information.

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public User parseUser(JSONObject jsonObject){
//        User result;
//        JSONArray metaData = jsonObject.getJSONArray("meta_data");
//        int points = 0;
//        for(int i = 0; i < metaData.length(); i++){
//            JSONObject object = metaData.getJSONObject(i);
//            if ( object.get("key").equals("_ywpar_user_total_points")){
//                points = Integer.parseInt(object.get("value").toString());
//                Log.d("POINTS", points + "");
//            }
//        }
//        String email = jsonObject.getString("email");
//        int id = Integer.parseInt(jsonObject.get("id").toString());
//        String userName = jsonObject.get("username").toString();
//
//        Log.d("POINTS", points + "");
//        result = new User(id, email, userName, points);
//
//        String firstname = jsonObject.getString("first_name");
//        String lastName = jsonObject.getString("last_name");
//        JSONObject adress = jsonObject.getJSONObject("billing");
//        String adress_1 = adress.getString("address_1");
//        String street = adress.getString("");
//        String number = "";
//        String streetAndNumber[] = adress_1.split(" ");
//        if(streetAndNumber.length > 1){
//            street = streetAndNumber[0];
//            number = streetAndNumber[1];
//        }
//        String postCode = adress.getString("postcode");
//        String city = adress.getString("city");
//        String state = adress.getString("state");
//        String country = adress.getString("country");
//
//        Customer customer = new Customer(id, firstname, lastName, email, street, number, postCode, city, state, country);
//        lastCustomer = customer;
//
//        return result;
//    }

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
}

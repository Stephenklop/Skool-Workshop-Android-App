package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import android.util.Log;

import com.example.skoolworkshop2.dao.UserDAO;
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

                JSONArray metaData = user.getJSONArray("meta_data");
                int points = 0;
                for(int i = 0; i < metaData.length(); i++){
                    JSONObject object = metaData.getJSONObject(i);
                    if ( object.get("key").equals("_ywpar_user_total_points")){
                        points = Integer.parseInt(object.get("value").toString());
                        Log.d("POINTS", points + "");
                    }
                }
                String token = user.get("token").toString();
                int id = Integer.parseInt(user.get("id").toString());
                String userName = user.get("username").toString();

                Log.d("POINTS", points + "");
                result = new User(token, id, userName, points);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

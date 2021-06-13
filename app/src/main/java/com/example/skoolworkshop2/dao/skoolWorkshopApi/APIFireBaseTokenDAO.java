package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.skoolworkshop2.dao.FireBaseTokenDAO;
import com.example.skoolworkshop2.logic.encryption.EncryptionLogic;
import com.example.skoolworkshop2.ui.MainActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class APIFireBaseTokenDAO implements FireBaseTokenDAO {
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void deleteToken(String token, int id) {
        try{
            connect(BASE_URL + "/customer/" + id + "/firebase");
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");
            String jsonInput = "{\"firebase_token\": \"" + token + "\"}";
            System.out.println("JSON STRING: " + jsonInput);

            OutputStream os = connection.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                System.out.println("FIREBASE TOKEN ADD:" + inputLine);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addToken(String token, int id) {
        try{
            connect(BASE_URL + "/customer/" + id + "/firebase");
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");
            String jsonInput = "{\"firebase_token\": \"" + token + "\"}";
            System.out.println("JSON STRING: " + jsonInput);

            OutputStream os = connection.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            System.out.println("INPUT" + in.readLine());
//            while ((inputLine = in.readLine()) != null){
//                System.out.println(inputLine);
//            }
        } catch (Exception e){
            System.out.println("FAILED");
            e.printStackTrace();
        }
    }
}

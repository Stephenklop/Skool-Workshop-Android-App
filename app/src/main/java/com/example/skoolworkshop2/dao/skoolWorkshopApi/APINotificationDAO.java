package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import com.example.skoolworkshop2.dao.NotificationDAO;
import com.example.skoolworkshop2.dao.localDatabase.entities.Notification;
import com.example.skoolworkshop2.domain.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class APINotificationDAO implements NotificationDAO {
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api/";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }


    @Override
    public List<Notification> getNotificationsForUser(int id) {
        final String PATH = "notifications/" + id;
        List<Notification> result = new ArrayList<>();

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject response = new JSONObject(inputLine);
                JSONArray notifications = response.getJSONArray("result");

                for (int i = 0; i < notifications.length(); i++) {
                    JSONObject jsonObject = (JSONObject) notifications.get(i);
                    result.add(parseNotification(jsonObject));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Notification> getNotificationsForTopic(String topic) {
        final String PATH = "notifications/topic/" + topic;
        List<Notification> result = new ArrayList<>();

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject response = new JSONObject(inputLine);
                JSONArray notifications = response.getJSONArray("result");

                for (int i = 0; i < notifications.length(); i++) {
                    JSONObject jsonObject = (JSONObject) notifications.get(i);
                    result.add(parseNotification(jsonObject));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private Notification parseNotification(JSONObject jsonObject){
        Notification result = null;
        String title = "";
        String description = "";
        String url = "";
        int id = 0;
        boolean status = false;

        try{
            title = jsonObject.getString("Title");
            description = jsonObject.getString("Message");
            url = jsonObject.getString("Url");
            id = jsonObject.getInt("Id");

            result = new Notification(id, title, description, url, status);
        }catch (Exception e){

        }
        return result;
    }
}

package com.example.skoolworkshop2.dao.mollie;

import com.example.skoolworkshop2.domain.Bank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MollieAPIService {
    private HttpURLConnection conn;

    private void connect(String url) throws IOException {
        URL connUrl = new URL("https://skool-workshop-api.herokuapp.com/api" + url);
        conn = (HttpURLConnection) connUrl.openConnection();
    }

    private void disconnect() {
        conn.disconnect();
    }

    public List<Bank> getAllIdealBanks() {
        List<Bank> result = new ArrayList<>();

        try {
            connect("/payment/ideal");
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while((inputLine = in.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(inputLine);
                for(int i = 0; i < jsonObject.getJSONArray("issuers").length(); i++) {
                    String id = jsonObject.getJSONArray("issuers").getJSONObject(i).getString("id");
                    String name = jsonObject.getJSONArray("issuers").getJSONObject(i).getString("name");
                    String svg = jsonObject.getJSONArray("issuers").getJSONObject(i).getJSONObject("image").getString("size1x");


                    result.add(new Bank(id, name, svg));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void createPayment() {
        
    }
}

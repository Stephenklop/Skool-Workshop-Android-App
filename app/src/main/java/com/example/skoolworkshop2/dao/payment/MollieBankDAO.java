package com.example.skoolworkshop2.dao.payment;

import com.example.skoolworkshop2.domain.Bank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MollieBankDAO implements BankDAO {
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api/";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    public List<Bank> getAllBanks() {
        final String PATH = "payment/ideal";
        List<Bank> result = new ArrayList<>();

        try {
            connect(BASE_URL + PATH);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject response = new JSONObject(inputLine);
                JSONArray workshops = response.getJSONArray("issuers");

                for (int i = 0; i < workshops.length(); i++) {
                    JSONObject jsonObject = (JSONObject) workshops.get(i);
                    result.add(parseBank(jsonObject));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private Bank parseBank(JSONObject jsonObject) {
        Bank result = null;

        try {
            result = new Bank(
                    jsonObject.getString("id"),
                    jsonObject.getString("name"),
                    jsonObject.getJSONObject("image").getString("svg")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
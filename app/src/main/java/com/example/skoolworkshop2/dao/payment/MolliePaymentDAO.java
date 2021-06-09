package com.example.skoolworkshop2.dao.payment;

import com.example.skoolworkshop2.domain.Bank;
import com.example.skoolworkshop2.domain.Payment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MolliePaymentDAO implements PaymentDAO {
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api/";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    @Override
    public Payment addPayment(int orderId, String amount, String description, Bank bank) {
        final String PATH = "payment";
        Payment result = null;

        try {
            connect(BASE_URL + PATH);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonInput = "{\"orderId\": \"" + orderId + "\", \"paymentAmount\": \"" + amount + "\", \"paymentDescription\": \"" + description + "\", \"bankId\": \"" + bank.getId() +  "\"}";

            OutputStream os = connection.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("RESPONSE: " + inputLine);
                JSONObject response = new JSONObject(inputLine);

                result = parsePayment(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private Payment parsePayment(JSONObject jsonObject) {
        Payment result = null;

        try {
            result = new Payment(jsonObject.getString("resource"), jsonObject.getString("id"), jsonObject.getString("mode"), jsonObject.getString("createdAt"), jsonObject.getJSONObject("amount").getString("value"), jsonObject.getJSONObject("amount").getString("currency"), jsonObject.getString("description"), jsonObject.getString("method"), jsonObject.getString("status"), jsonObject.getString("expiresAt"), jsonObject.getString("locale"), jsonObject.getString("redirectUrl"), jsonObject.getString("webhookUrl"), jsonObject.getJSONObject("_links").getJSONObject("checkout").getString("href"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}

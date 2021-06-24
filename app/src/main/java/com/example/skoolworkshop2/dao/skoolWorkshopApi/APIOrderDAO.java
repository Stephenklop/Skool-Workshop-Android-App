package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import android.util.Log;
import androidx.room.FtsOptions;

import com.example.skoolworkshop2.dao.OrderDAO;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.Reservation;
import com.example.skoolworkshop2.domain.ShippingAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.ui.SplashScreenActivity;

import java.io.OutputStream;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class APIOrderDAO implements OrderDAO {

    private final String TAG = getClass().getSimpleName();
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api/";

    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    @Override
    public List<Reservation> getAllReservationsFromUser(int userId) {
        List<Reservation> orders = new ArrayList<>();
        final String PATH = "order/71";

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                JSONArray input = new JSONArray(line);
                System.out.println(input);

                for(int i = 0; i < input.length(); i++){
                    Reservation reservation = parseReservation(input.getJSONObject(i));
                    System.out.println(reservation.toString());
                    orders.add(reservation);
                    Log.d(TAG, "order: " + parseReservation(input.getJSONObject(i)));

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getAllReservationsFromUser: " + orders.size());
        return orders;
    }

    public Reservation parseReservation(JSONObject object) throws JSONException {
        Reservation order = null;
        try {
            int id = object.getInt("id");
            String status = object.getString("status");
            int costumerId = object.getInt("customer_id");
            String date = object.getString("date_created");
            JSONArray items = object.getJSONArray("line_items");
            String type = "";
            if(items.length() > 1){
                for(int i = 0; i < items.length(); i++){
                    if(i == 0){
                        JSONObject item = items.getJSONObject(i);
                        type += item.getString("name");
                    } else {
                        JSONObject item = items.getJSONObject(i);
                        type += ", " + item.getString("name");
                    }
                }
            } else if(items.length() == 1){
                JSONObject item = items.getJSONObject(0);
                type += item.getString("name");
            }


            order = new Reservation(id, status, date, costumerId, type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public Order addOrder(Order order) {
        Order result = null;
        final String PATH = "order";

        try {
            connect(BASE_URL + PATH);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");

            String jsonInput = parseOrderToJson(order);
            System.out.println("JSON STRING: " + jsonInput);

            OutputStream os = connection.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();

            System.out.println(connection.getRequestMethod());
            System.out.println(connection.getResponseCode());


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject resultObject = new JSONObject(inputLine).getJSONObject("result");
                result = parseJsonToOrder(resultObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean updateOrderStatus(int id, String status) {
        boolean result = false;
        final String PATH = "order/" + id;

        try {
            connect(BASE_URL + PATH);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");

            String jsonInput = "{\"status\": \"" + status + "\"}";

            OutputStream os = connection.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();

            result = connection.getResponseCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Order parseJsonToOrder(JSONObject jsonObject) {
        Order result = null;

        try {
            JSONObject orderInformation = jsonObject.getJSONObject("orderInformation");
            JSONArray bookingInformation = jsonObject.getJSONArray("bookingInformation");

            result = new Order(
                    orderInformation.getString("status"),
                    bookingInformation.getJSONObject(0).getJSONObject("result").getInt("customerId"),
                    0,
                    0,
                    orderInformation.getString("payment_method"),
                    orderInformation.getString("payment_method_title"),
                    orderInformation.getString("customer_note"),
                    orderInformation.getJSONArray("meta_data").getJSONObject(0).getString("value"),
                    orderInformation.getJSONArray("meta_data").getJSONObject(3).getString("value"),
                    orderInformation.getJSONArray("meta_data").getJSONObject(2).getString("value"),
                    orderInformation.getDouble("shipping_total"),
                    orderInformation.getDouble("total")
            );

            result.setId(orderInformation.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String parseOrderToJson(Order order) {
        LocalDb localDb = LocalDb.getDatabase(SplashScreenActivity.application);
        Customer customer = localDb.getCustomerDAO().getCustomer();
        BillingAddress billingAddress = localDb.getBillingAddressDAO().getBillingAddress();
        ShippingAddress shippingAddress = localDb.getShippingAddressDAO().getShippingAddress();
        List<ShoppingCartItem> shoppingCartItems = LocalDb.getDatabase(SplashScreenActivity.application).getShoppingCartDAO().getItemsInShoppingCart();
        StringBuilder result = new StringBuilder();

        result.append("{" +
                "  \"status\": \"" + order.getStatus() + "\"," +
                "  \"customer_id\": " + order.getCustomerId() + "," +
                "  \"billing\": {" +
                "    \"first_name\": \"" + customer.getFirstName() + "\"," +
                "    \"last_name\": \"" + customer.getLastName() + "\"," +
                "    \"company\": \"" + billingAddress.getCompany() + "\"," +
                "    \"address_1\": \"" + billingAddress.getAddress() + "\"," +
                "    \"address_2\": \"\"," +
                "    \"city\": \"" + billingAddress.getCity() + "\"," +
                "    \"state\": \"" + billingAddress.getState() + "\"," +
                "    \"postcode\": \"" + billingAddress.getPostcode() + "\"," +
                "    \"country\": \"" + billingAddress.getCountry() + "\"," +
                "    \"email\": \"" + billingAddress.getEmail() + "\"," +
                "    \"phone\": \"" + billingAddress.getPhone() + "\"" +
                "  }," +
                "  \"shipping\": {" +
                "    \"first_name\": \"" + customer.getFirstName() + "\"," +
                "    \"last_name\": \"" + customer.getLastName() + "\"," +
                "    \"company\": \"" + shippingAddress.getCompany() + "\"," +
                "    \"address_1\": \"" + shippingAddress.getAddress() + "\"," +
                "    \"address_2\": \"\"," +
                "    \"city\": \"" + shippingAddress.getCity() + "\"," +
                "    \"state\": \"" + shippingAddress.getState() + "\"," +
                "    \"postcode\": \"" + shippingAddress.getPostcode() + "\"," +
                "    \"country\": \"" + shippingAddress.getCountry() + "\"" +
                "  }," +
                "  \"payment_method\": \"" + order.getPaymentMethod() + "\"," +
                "  \"payment_method_title\": \"" + order.getPaymentMethodTitle() + "\"," +
                "  \"customer_note\": \"" + order.getCustomerNote() + "\"," +
                "  \"billing_CJP\": " + order.getBillingCJP() + "," +
                "  \"billing_video\": \"" + order.getBillingVideo() + "\"," +
                "  \"reservation_system\": \"" + order.getReservationSystem() + "\"," +
                "  \"line_items\": ["
        );

        for (int i = 0; i < shoppingCartItems.size(); i++) {
            result.append(parseShoppingCartItemToJson(shoppingCartItems.get(i)));

            if (shoppingCartItems.size() != i + 1) {
                result.append(",");
            }
        }

        result.append("]," +
                    "  \"shipping_lines\": {" +
                    "    \"distance\": 7.2," +
                    "    \"price\": 4.03" +
                    "  }" +
                    "}"
        );

        return result.toString();
    }

    private String parseShoppingCartItemToJson(ShoppingCartItem shoppingCartItem) {
        StringBuilder result = new StringBuilder();

        Product product = LocalDb.getDatabase(SplashScreenActivity.application).getProductDAO().getProduct(shoppingCartItem.getProductId());

        result.append("{" +
                "      \"name\": \"" + product.getName() + "\"," +
                "      \"product_id\": " + product.getProductId() + "," +
                "      \"quantity\": 1," +
                "      \"subtotal\": " + shoppingCartItem.getTotalPrice() + "," +
                "      \"total\": " + (shoppingCartItem.getTotalPrice() + shoppingCartItem.getAmountOfParticipantsGraffitiTshirt() * 7.50) + "," +
                "      \"participants\": " + shoppingCartItem.getParticipants() + "," +
                "      \"participants_total_cost\": " + (shoppingCartItem.getAmountOfParticipantsGraffitiTshirt() * 7.50) + "," +
                "      \"workshop_rounds\": " + shoppingCartItem.getRounds() + "," +
                "      \"workshop_round_minutes\": " + shoppingCartItem.getRoundDuration() + ",");

        if (shoppingCartItem.isWorkshop()) {
            result.append("\"total_duration\": " + (shoppingCartItem.getRoundDuration() * shoppingCartItem.getRounds()) + "," +
                    "      \"total_duration_price\": " + shoppingCartItem.getTotalPrice() + ",");
        } else {
            result.append("\"culture_day\": {" +
                    "        \"workshops_per_round\": " + shoppingCartItem.getWorkshopPerWorkshopRound() + "," +
                    "        \"workshops\": ["
            );

            for (int i = 0; i < shoppingCartItem.getProductIdsList().size(); i++) {
                int productId = shoppingCartItem.getProductIdsList().get(i);

                result.append("\"" + productId + "\"");

                if (shoppingCartItem.getProductIdsList().size() != i + 1) {
                    result.append(",");
                }
            }

            result.append("]," +
                    "        \"total_participants\": 20," +
                    "        \"price\": 1674," +
                    "        \"total_minutes\": 720" +
                    "      }," +
                    "      \"total_duration\": 0," +
                    "      \"total_duration_price\": 0,");

        }

        result.append("\"timetable\": \"" + shoppingCartItem.getTimeSchedule() + "\"," +
                "      \"learning_level\": \"" + shoppingCartItem.getLearningLevel() + "\"," +
                "      \"booking_info\": {" +
                "        \"start_date\": \"" + shoppingCartItem.getStartDate() + "\"," +
                "        \"end_date\": \"" + shoppingCartItem.getEndDate() + "\"," +
                "        \"booking_status\": \"unpaid\"" +
                "      }" +
                "    }");

        return result.toString();
    }
}

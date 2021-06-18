package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import com.example.skoolworkshop2.dao.OrderDAO;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.Order;
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
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api/";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public List<Order> getAllOrdersOfUser(int userId) {
        List<Order> orders = new ArrayList<>();
        final String PATH = "/order/" + userId;

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONArray input = new JSONArray(inputLine);
                for(int i = 0; i < input.length(); i++){
                    orders.add(parseJsonToOrder(input.getJSONObject(i)));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public Order getOrder(int id) {
        return null;
    }

    @Override
    public void addOrder(Order order) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String parseOrderToJson(Order order) {
        LocalDb localDb = LocalDb.getDatabase(SplashScreenActivity.application);
        Customer customer = localDb.getCustomerDAO().getCustomer();
        BillingAddress billingAddress = localDb.getBillingAddressDAO().getBillingAddress();
        ShippingAddress shippingAddress = localDb.getShippingAddressDAO().getShippingAddress();
        List<ShoppingCartItem> shoppingCartItems = LocalDb.getDatabase(SplashScreenActivity.application).getShoppingCartDAO().getItemsInShoppingCart();
        StringBuilder result = new StringBuilder();

        result.append("{\n" +
                "  \"status\": \"" + order.getStatus() + "\",\n" +
                "  \"customer_id\": " + order.getCustomerId() + ",\n" +
                "  \"billing\": {\n" +
                "    \"first_name\": \"" + customer.getFirstName() + "\",\n" +
                "    \"last_name\": \"" + customer.getLastName() + "\",\n" +
                "    \"company\": \"" + billingAddress.getCompany() + "\",\n" +
                "    \"address_1\": \"" + billingAddress.getAddress() + "\",\n" +
                "    \"address_2\": \"\",\n" +
                "    \"city\": \"" + billingAddress.getCity() + "\",\n" +
                "    \"state\": \"" + billingAddress.getState() + "\",\n" +
                "    \"postcode\": \"" + billingAddress.getPostcode() + "\",\n" +
                "    \"country\": \"" + billingAddress.getCountry() + "\",\n" +
                "    \"email\": \"" + billingAddress.getEmail() + "\",\n" +
                "    \"phone\": \"" + billingAddress.getPhone() + "\"\n" +
                "  },\n" +
                "  \"shipping\": {\n" +
                "    \"first_name\": \"" + customer.getFirstName() + "\",\n" +
                "    \"last_name\": \"" + customer.getLastName() + "\",\n" +
                "    \"company\": \"" + shippingAddress.getCompany() + "\",\n" +
                "    \"address_1\": \"" + shippingAddress.getAddress() + "\",\n" +
                "    \"address_2\": \"\",\n" +
                "    \"city\": \"" + shippingAddress.getCity() + "\",\n" +
                "    \"state\": \"" + shippingAddress.getState() + "\",\n" +
                "    \"postcode\": \"" + shippingAddress.getPostcode() + "\",\n" +
                "    \"country\": \"" + shippingAddress.getCountry() + "\"\n" +
                "  },\n" +
                "  \"payment_method\": \"" + order.getPaymentMethod() + "\",\n" +
                "  \"payment_method_title\": \"" + order.getPaymentMethodTitle() + "\",\n" +
                "  \"customer_note\": \"" + order.getCustomerNote() + "\",\n" +
                "  \"billing_CJP\": " + order.getBillingCJP() + ",\n" +
                "  \"billing_video\": \"" + order.getBillingVideo() + "\",\n" +
                "  \"reservation_system\": \"" + order.getReservationSystem() + "\",\n" +
                "  \"line_items\": [\n"
        );

        for (int i = 0; i < shoppingCartItems.size(); i++) {
            result.append(parseShoppingCartItemToJson(shoppingCartItems.get(i)));

            if (shoppingCartItems.size() != i + 1) {
                result.append(",\n");
            }
        }

        result.append("],\n" +
                    "  \"shipping_lines\": {\n" +
                    "    \"distance\": 7.2,\n" +
                    "    \"price\": 4.03\n" +
                    "  }\n" +
                    "}"
        );

        return result.toString();
    }

    private String parseShoppingCartItemToJson(ShoppingCartItem shoppingCartItem) {
        StringBuilder result = new StringBuilder();

        Product product = LocalDb.getDatabase(SplashScreenActivity.application).getProductDAO().getProduct(shoppingCartItem.getProductId());

        result.append("{\n" +
                "      \"name\": \"" + product.getName() + "\",\n" +
                "      \"product_id\": " + product.getProductId() + ",\n" +
                "      \"quantity\": 1,\n" +
                "      \"subtotal\": " + shoppingCartItem.getTotalPrice() + ",\n" +
                "      \"total\": " + (shoppingCartItem.getTotalPrice() + shoppingCartItem.getAmountOfParticipantsGraffitiTshirt() * 7.50) + ",\n" +
                "      \"participants\": " + shoppingCartItem.getParticipants() + ",\n" +
                "      \"participants_total_cost\": " + (shoppingCartItem.getAmountOfParticipantsGraffitiTshirt() * 7.50) + ",\n" +
                "      \"workshop_rounds\": " + shoppingCartItem.getRounds() + ",\n" +
                "      \"workshop_round_minutes\": " + shoppingCartItem.getRoundDuration() + ",\n");

        if (shoppingCartItem.isWorkshop()) {
            result.append("\"total_duration\": " + (shoppingCartItem.getRoundDuration() * shoppingCartItem.getRounds()) + ",\n" +
                    "      \"total_duration_price\": " + shoppingCartItem.getTotalPrice() + ",\n");
        } else {
            result.append("\"culture_day\": {\n" +
                    "        \"workshops_per_round\": " + shoppingCartItem.getWorkshopPerWorkshopRound() + ",\n" +
                    "        \"workshops\": [\n"
            );

            for (int i = 0; i < shoppingCartItem.getProductIdsList().size(); i++) {
                int productId = shoppingCartItem.getProductIdsList().get(i);

                result.append("\"" + productId + "\"");

                if (shoppingCartItem.getProductIdsList().size() != i + 1) {
                    result.append(",\n");
                }
            }

            result.append("],\n" +
                    "        \"total_participants\": 20,\n" +
                    "        \"price\": 1674,\n" +
                    "        \"total_minutes\": 720\n" +
                    "      },\n" +
                    "      \"total_duration\": 0,\n" +
                    "      \"total_duration_price\": 0,\n");

        }

        // TODO: Fix \n at timeschedule
        result.append("\"timetable\": \"" + shoppingCartItem.getTimeSchedule() + "\",\n" +
                "      \"learning_level\": \"" + shoppingCartItem.getLearningLevel() + "\",\n" +
                "      \"booking_info\": {\n" +
                "        \"start_date\": \"\",\n" +
                "        \"end_date\": \"\",\n" +
                "        \"booking_status\": \"unpaid\"\n" +
                "      }\n" +
                "    }");

        return result.toString();
    }

    public Order parseJsonToOrder(JSONObject object) throws JSONException {
        Order order = null;
        BillingAddress address = (BillingAddress) object.get("billing");
        ShippingAddress shippingAddress = (ShippingAddress) object.get("shipping");

        try {
            String status = object.getString("status");
            int costumerId = object.getInt("costumer_id");
            int billingId = address.getId();
            int shippingId = shippingAddress.getId();
            String paymentMethod = object.getString("payment_method");
            String paymentMethodTitle = object.getString("payment_method_title");
            String costumerNote = object.getString("customer_note");
            int billingCJP = object.getInt("billing_CJP");
            String billingVideo = object.getString("billing_video");
            String reservationSystem = object.getString("reservation_system");
            JSONArray shippingLines = object.getJSONArray("shipping_lines");
            JSONObject shippingObject = shippingLines.getJSONObject(0);
            Double distance = shippingObject.getDouble("distance");
            Double price = shippingObject.getDouble("price");
            order = new Order(status, costumerId, billingId, shippingId, paymentMethod, paymentMethodTitle, costumerNote, billingCJP, billingVideo, reservationSystem, distance, price);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return order;
    }
}

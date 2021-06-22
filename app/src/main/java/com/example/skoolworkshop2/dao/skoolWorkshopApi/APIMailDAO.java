package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import com.example.skoolworkshop2.dao.EmailDAO;
import com.example.skoolworkshop2.domain.Mail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class APIMailDAO implements EmailDAO {
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    @Override
    public void sendMail(Mail mail) {
        try{
            connect(BASE_URL + "/email/info");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");
            String jsonInput = "{\"aantalPersonen\": \"" + mail.getAmountOfPerson() + "\", \"gewensteDatum\":\"" + mail.getDate() + "\",\"gewensteAanvangstijd\":\"" + mail.getTime() + "\",\"cjpSchoolnummer\":\"" + mail.getCjp() + "\",\"email\":\"" + mail.getEmail() + "\",\"telefoonnummer\":\"" + mail.getPhone() + "\",\"bericht\":\"" + mail.getMessage() + "\", \"testEmail\":\"bbuijsen@gmail.com\"}";
            System.out.println("JSON STRING: " + jsonInput);

            OutputStream os = connection.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                System.out.println("EMAIL SENT:" + inputLine);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

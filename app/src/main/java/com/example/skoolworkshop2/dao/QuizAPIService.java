package com.example.skoolworkshop2.dao;

import android.os.AsyncTask;

import com.example.skoolworkshop2.domain.Quiz;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuizAPIService{
    private HttpURLConnection conn;

    private void connect(String url) throws IOException {
        URL connUrl = new URL("https://skool-workshop-api.herokuapp.com/api" + url);
        conn = (HttpURLConnection) connUrl.openConnection();
    }

    private void disconnect() {
        conn.disconnect();
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> result = new ArrayList<>();

        try {
            connect("/quiz");
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while((inputLine = in.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(inputLine);
                JSONObject quizzes = jsonObject.getJSONObject("result");
                for(int i = 0; i < quizzes.getJSONArray("quiz").length(); i++) {
                    String title = quizzes.getJSONArray("quiz").getJSONObject(i).getString("Title");
                    String url = quizzes.getJSONArray("quiz").getJSONObject(i).getString("Url");
                    boolean status = Boolean.parseBoolean((quizzes.getJSONArray("quiz").getJSONObject(i).getString("Status")));

                    result.add(new Quiz(title, url, status));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

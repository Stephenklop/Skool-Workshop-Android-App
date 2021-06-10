package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import com.example.skoolworkshop2.dao.NewsArticleDAO;
import com.example.skoolworkshop2.domain.NewsArticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class APINewsArticleDAO implements NewsArticleDAO {
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    public List<NewsArticle> getAllArticles() {
        List<NewsArticle> resultList = new ArrayList<>();
        final String PATH = "/news";

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject input = new JSONObject(inputLine);
                JSONArray response = input.getJSONArray("result");
                System.out.println("ARTICLES: " + response.length());

                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonListObject = (JSONObject) response.get(i);
                    System.out.println("SINGLE ARTICLE: " + parseArticle(jsonListObject));
                    resultList.add(parseArticle(jsonListObject));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public NewsArticle parseArticle(JSONObject jsonObject) {

        NewsArticle result;

        int id = -1;
        String url = "";
        String imgUrlHTML = "";
        String imgUrl = "";
        String name = "";
        String date = "";

        //Get url, imgUrlHTML and name from objects json
        try {
            id = jsonObject.getInt("id");
            url = jsonObject.getString("site_url");
            imgUrlHTML = jsonObject.getString("img_url");
            name = jsonObject.getString("title");
            date = jsonObject.getString("date").replace("T", " ") + ".000";
            System.out.println(date);
            imgUrl = jsonObject.getString("img_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        result = new NewsArticle(id, url, imgUrl, name, date);
        System.out.println(result);

        return result;

    }
}

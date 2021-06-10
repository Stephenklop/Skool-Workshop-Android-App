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
    private final String BASE_URL = "https://skoolworkshop.nl/wp-json/wp/v2/";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }

    public List<NewsArticle> getAllArticles() {
        List<NewsArticle> resultList = new ArrayList<>();
        final String PATH = "posts";

        try {
            connect(BASE_URL + PATH);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONArray response = new JSONArray(inputLine);
                System.out.println("ARTICLES: " + response.length());

                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonListObject = (JSONObject) response.get(i);
                    System.out.println("SINGLE ARTICLE: " + parseArticle(jsonListObject));
                    resultList.add(parseArticle(jsonListObject));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            url = jsonObject.getString("link");
            JSONObject imgUrlHTMLObject = jsonObject.getJSONObject("content");
            imgUrlHTML = imgUrlHTMLObject.getString("rendered");
            JSONObject titleObject = jsonObject.getJSONObject("title");
            name = titleObject.getString("rendered");
            date = jsonObject.getString("date").replace("T", " ") + ".000";
            System.out.println(date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //use Regex to search for image URL's in HTML text
        //all URL's are stored in this list
        List<String> pics = new ArrayList<String>();
        String img = "";
        Pattern pattern;
        Matcher matcher;

        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        pattern = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(imgUrlHTML);

        while (matcher.find()) {
            // get <img /> data
            img = matcher.group();
            // match the src data in <img>
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        System.out.println(pics);

        imgUrl = ((pics.size() > 0) ? pics.get(0) : "https://skoolworkshop.nl/wp-content/uploads/2019/11/Skool-homepage-1-300x300.jpg");

        result = new NewsArticle(id, url, imgUrl, name, date);


        return result;

    }
}

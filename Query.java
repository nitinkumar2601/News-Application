package com.example.android.dekhonews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class Query
{
    private Query(){
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";

        if(url==null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setReadTimeout(10000);
//            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        }catch (IOException e){

        }finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while(line!=null)
            {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<News> extractFeatureFromJson(String newsJson)
    {
        if(TextUtils.isEmpty(newsJson))
            return null;

        List<News> news = new ArrayList<>();

        try
        {

            JSONObject root = new JSONObject(newsJson);
            JSONArray newsArray = root.optJSONArray("articles");

            for(int i = 0; i< Objects.requireNonNull(newsArray).length(); i++)
            {
                JSONObject currentNews = newsArray.getJSONObject(i);
                String title = currentNews.getString("title");
                String url = currentNews.getString("url");
                String thumbnail = currentNews.getString("image");
                String publishedAt = currentNews.getString("publishedAt");

                News newsObject = new News(title,url,thumbnail,publishedAt);
                news.add(newsObject);
            }
        }catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return news;
    }

    /**
     * Query the USGS dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {

        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return news;
    }
}

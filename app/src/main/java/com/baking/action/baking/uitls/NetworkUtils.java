package com.baking.action.baking.uitls;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by hanyuezi on 17/10/15.
 */

public class NetworkUtils {

    public static String IMG_BASE_URL = "http://image.tmdb.org/t/p/w185//";

    //电影列表base url
    public static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";

    private static String getApiKey(Activity activity){
        String apiKey = "";
        Properties pro = new Properties();
        InputStream is = null;
        try {
            is = activity.getAssets().open("config.properties");
            pro.load(is);
            apiKey =  pro.getProperty("apikey");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiKey;
    }

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param activity The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(Activity activity, String baseUrl) {
        // COMPLETED (1) Fix this method to return the URL used to query Open Weather Map's API
        Uri builtUri = Uri.parse(baseUrl).buildUpon().build();
        Log.e("NetworkUtils buildUrl", builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrl(Activity activity, String baseUrl, String param2) {
        // COMPLETED (1) Fix this method to return the URL used to query Open Weather Map's API
        Uri builtUri = Uri.parse(baseUrl + getApiKey(activity) + param2).buildUpon()
                .build();
        Log.e("NetworkUtils buildUrl", builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.e("", "getResponseFromHttpUrl......................" + url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(30000);
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

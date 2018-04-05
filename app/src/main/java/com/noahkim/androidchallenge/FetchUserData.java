package com.noahkim.androidchallenge;

import android.net.Uri;
import android.os.AsyncTask;

import com.noahkim.androidchallenge.data.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class FetchUserData extends AsyncTask<String, Void, List<Item>> {

    public FetchUserData() {
    }
    private static List<Item> getDataFromJson(String jsonResponse) {

        // Create an empty ArrayList that we can start adding users to
        List<Item> users = new ArrayList<>();

        // Parse JSON response string
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray jsonArray = baseJsonResponse.getJSONArray("items");

            // For each user in the array, create a user object
            for (int i = 0; i < jsonArray.length(); i++) {

                // Get a single user at position i within the list
                JSONObject object = jsonArray.getJSONObject(i);

                String username = object.getString("display_name");
                String gravatar = object.getString("profile_image");

                // Create a new user object and add it to the list of users
//                Item user = new Item();
//                users.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }
    @Override
    protected List<Item> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        List<Item> users = null;
        String jsonString;

        Uri uri = new Uri.Builder()
                .scheme("https")
                .authority("api.stackexchange.com")
                .appendPath("2.2")
                .appendPath("users")
                .appendQueryParameter("site", "stackoverflow")
                .build();

        try {
            URL url = new URL(uri.toString());
            Timber.d(url.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            // Get response as a string and extract data from JSON
            jsonString = buffer.toString();
            users = getDataFromJson(jsonString);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    @Override
    protected void onPostExecute(List<Item> users) {

        super.onPostExecute(users);
    }
}

package com.android.noahkim_androidchallenge;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class FetchUserData extends AsyncTask<String, Void, List<User>> {

    public FetchUserData() {
    }
    private static List<User> getDataFromJson(String jsonResponse) throws JSONException, ParseException {

        final String ARRAY_ITEMS = "items";
        final String USERNAME = "display_name";
        final String GRAVATAR = "profile_image";

        // Create an empty ArrayList that we can start adding users to
        List<User> users = new ArrayList<>();

        // Parse JSON response string
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray jsonArray = baseJsonResponse.getJSONArray(ARRAY_ITEMS);

            // For each user in the array, create a user object
            for (int i = 0; i < jsonArray.length(); i++) {

                // Get a single user at position i within the list of movies
                JSONObject user = jsonArray.getJSONObject(i);

                String username = user.getString(USERNAME);
                String gravatar = user.getString(GRAVATAR);

                // Create a new Review object and add it to the list of users
                User review = new User(username, gravatar);
                users.add(review);
            }
            Timber.d("FetchUserData Complete");

        } catch (JSONException e) {
            Timber.e("Problem parsing the users JSON results", e);
            e.printStackTrace();
        }
        return users;
    }
    @Override
    protected List<User> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        List<User> users = null;
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

        } catch (IOException | ParseException | JSONException e) {
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
    protected void onPostExecute(List<User> users) {

        super.onPostExecute(users);
    }
}

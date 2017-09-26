package com.jennyjspartaglobal.spartasnippets;

import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tech-A42 on 26/09/2017.
 */

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String SNIPPETS_URL = "https://spartasnippets.herokuapp.com/pi.json";
    private QueryUtils() {

    }

    public static Snippet fetchSnippetData() {
        URL url = createUrl(SNIPPETS_URL);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream ", e);
        }
        Snippet snippet = extractSnippetFromJson(jsonResponse);
        return snippet;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating url ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the results ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static Snippet extractSnippetFromJson(String snippetJson) {
        if (TextUtils.isEmpty(snippetJson)) {
            return null;
        }

        String[] snippet = new String[2];

        try {
            JSONObject baseJsonResponse = new JSONObject(snippetJson);

            String question = baseJsonResponse.getString("question");
            String answer = baseJsonResponse.getString("answer");

            snippet[0] = question;
            snippet[1] = answer;

            return new Snippet(question, answer);


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing JSON");
        } finally {

        }
        return null;
    }

}

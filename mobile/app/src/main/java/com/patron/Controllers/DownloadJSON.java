package com.patron.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class DownloadJSON extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {

            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        getProtege(s);
    }

    void getProtege(String s) {
        try{
            //JSONObject jsonObject = new JSONObject(s);

            JSONArray arr = new JSONArray(s);

            for(int i = 0; i < arr.length(); i++) {
                JSONObject jsonPart = arr.getJSONObject(i);
                Log.i("Firstname", jsonPart.getString("protege_firstname"));
                Log.i("Lastname", jsonPart.getString("protege_lastname"));
                Log.i("Age", jsonPart.getString("protege_age"));
                Log.i("ProtegePhone", jsonPart.getString("protege_phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


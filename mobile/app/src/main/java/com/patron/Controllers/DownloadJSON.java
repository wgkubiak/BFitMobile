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

    public static String tempMail = "";
    public static String tempPass = "";
    public static JSONArray tempArray;

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

        fetchData(s);

        try{
            for(int i = 0; i < fetchData(s).length(); i++) {
                JSONObject jsonPart = fetchData(s).getJSONObject(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JSONArray fetchData(String s) {
        try{
            JSONArray arr = new JSONArray(s);

            tempArray = arr;
            return arr;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}


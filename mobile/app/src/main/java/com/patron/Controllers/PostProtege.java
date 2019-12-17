package com.patron.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PostProtege extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        String urlString = params[0];
        String fistName = params[1];
        String lastName = params[2];
        String mail = params[3];
        String pass = params[4];

        URL url = null;
        InputStream stream = null;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String data = URLEncoder.encode("protege_firstname", "UTF-8")
                    + "=" + URLEncoder.encode(fistName, "UTF-8");

            data += "&" + URLEncoder.encode("protege_lastname", "UTF-8") + "="
                    + URLEncoder.encode(lastName, "UTF-8");

            data += "&" + URLEncoder.encode("protege_mail", "UTF-8") + "="
                    + URLEncoder.encode(mail, "UTF-8");

            data += "&" + URLEncoder.encode("protege_pass", "UTF-8") + "="
                    + URLEncoder.encode(pass, "UTF-8");

            urlConnection.connect();

            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(data);
            wr.flush();

            stream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
            String result = reader.readLine();
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("Result", "SLEEP ERROR");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}

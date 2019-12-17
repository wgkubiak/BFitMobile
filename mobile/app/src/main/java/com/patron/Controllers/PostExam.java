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

public class PostExam extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        String urlString = params[0];
        String examProtege = params[1];
        String examWeight = params[2];
        String examGlucose = params[3];
        String examPressure = params[4];

        URL url = null;
        InputStream stream = null;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String data = URLEncoder.encode("exam_protege", "UTF-8")
                    + "=" + URLEncoder.encode(examProtege, "UTF-8");

            data += "&" + URLEncoder.encode("exam_weight", "UTF-8") + "="
                    + URLEncoder.encode(examWeight, "UTF-8");

            data += "&" + URLEncoder.encode("exam_glucose", "UTF-8") + "="
                    + URLEncoder.encode(examGlucose, "UTF-8");

            data += "&" + URLEncoder.encode("exam_pressure", "UTF-8") + "="
                    + URLEncoder.encode(examPressure, "UTF-8");

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

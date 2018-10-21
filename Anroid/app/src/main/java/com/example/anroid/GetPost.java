package com.example.anroid;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class GetPost extends AsyncTask<HashMap<String, String>, Void, String> {

    private static final String myUrl = "http://192.168.43.68:50000/api/Web";//"http://desktop-dtjiv0n:9810/api/Rest";

    public interface AsyncResponse {
        void processFinish(String output) throws ExecutionException, InterruptedException;
    }

    public AsyncResponse delegate = null;

    public GetPost(AsyncResponse asyncResponse) {
        this.delegate = asyncResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            delegate.processFinish(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getQuery(HashMap<String, String> map) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();

        boolean isFirst = true;

        for (Map.Entry<String, String> item : map.entrySet()){
            if(isFirst){
                isFirst = false;
                builder.append('&');
            }

            builder.append(URLEncoder.encode(item.getKey(), "UTF-8"));
            builder.append('=');
            builder.append(URLEncoder.encode(item.getValue(), "UTF-8"));
        }

        return builder.toString();
    }

    @Override
    protected String doInBackground(HashMap<String, String>... maps) {
        try {
            URL url = new URL(myUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            /*connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");*/
            connection.setRequestProperty("ContentType", "application/json");

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            try {
                /*JSONObject object = new JSONObject();
                object.put("value", "25");
                bufferedWriter.write(URLEncoder.encode(object.toString(), "UTF-8"));*/
                bufferedWriter.write(getQuery(maps[0]));
                bufferedWriter.flush();

                int responseCode = connection.getResponseCode();

                String response = "";

                if(responseCode == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;

                    while((line = reader.readLine()) != null){
                        response += line;
                    }
                }

                return response;
            } catch (Exception exc) {
                exc.printStackTrace();
            } finally {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception gfdgdfg) {
            return null;
        }

        return null;
    }
}

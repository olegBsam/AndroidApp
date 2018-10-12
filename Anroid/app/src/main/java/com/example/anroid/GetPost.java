package com.example.anroid;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetPost {

    private static final String myUrl = "http://localhost:55658/api/Rest";//"http://desktop-dtjiv0n:9810/api/Rest";

    public static void doGet(String data) throws Exception {
        URL url = new URL(myUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("ContentType", "application/json");

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        try {
            bufferedWriter.write(data);
            bufferedWriter.flush();
        }
        catch (Exception exc){
            exc.printStackTrace();
            throw exc;
        }
        finally {
            bufferedWriter.close();
        }
    }

    public static void sendData(String data) {
        new AsyncTask<Void, String, String>(){
            @Override
            protected String doInBackground(Void... voids) {

                return "";
            }
        }.execute();
    }
}

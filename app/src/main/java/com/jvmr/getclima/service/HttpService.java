package com.jvmr.getclima.service;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HttpService extends AsyncTask<String, Void, StringBuilder> {


    @Override
    protected StringBuilder doInBackground(String... urls) {
        StringBuilder resposta = new StringBuilder();

        try {
            URL URL = new URL(urls[0]);

            HttpURLConnection connection = (HttpURLConnection) URL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(URL.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n============================");
        System.out.println(resposta.toString());
        System.out.println("============================\n");


        return resposta;
    }
}

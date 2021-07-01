package com.jvmr.getclima.datasource;

import com.jvmr.getclima.model.CidadeModel;
import com.jvmr.getclima.service.HttpService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class HGDataSource {
    static final private String baseURL = "https://api.hgbrasil.com/weather/";
    static final private String key = "0d1e6304";

    private static HGDataSource instance;

    public static HGDataSource getInstance() {
        if (instance == null) {
            instance = new HGDataSource();
        }
        return instance;
    }

    public CidadeModel buscarCidadePorGeoLoc(double latitude, double longitude) {
        CidadeModel cidadeModel = null;
        StringBuilder retorno;

        try {
            retorno = new HttpService().execute(baseURL + "?key=" + key + "&lat=" + latitude + "&lon=" + longitude + "user_ip=remote").get();

//            System.out.println("------------------retorno-------------------->");
//            System.out.println("\t\t\t"+retorno);
//            System.out.println("------------------retorno-------------------->");

            JSONObject reader = new JSONObject(retorno.toString());
            JSONObject results = reader.getJSONObject("results");
            cidadeModel = CidadeModel.readJSON(results);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return cidadeModel;
    }

    public CidadeModel buscarCidadePorNomeEstado(String cidade, String estado) {
        CidadeModel cidadeModel = null;
        StringBuilder retorno;

        try {
            String query = "&city_name=" + cidade + "," + estado;
            retorno = new HttpService().execute(baseURL + "?key=" + key + query).get();
            JSONObject reader = new JSONObject(retorno.toString());
            JSONObject results = reader.getJSONObject("results");
            cidadeModel = CidadeModel.readJSON(results);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return cidadeModel;
    }

}

package com.jvmr.getclima.datasource;

import com.google.gson.Gson;
import com.jvmr.getclima.model.CidadeModel;
import com.jvmr.getclima.service.HttpService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class HGDataSource {
    static final private String baseURL = "https://api.hgbrasil.com/weather/";
    static final private String key = "3a0b1b47";
    final private Gson gson = new Gson();

    private static HGDataSource instance;

    public static HGDataSource getInstance() {
        if (instance == null) {
            instance = new HGDataSource();
        }
        return instance;
    }

    public CidadeModel buscarCidadePorGeoLoc(float latitude, float longitude) {
        CidadeModel cidadeModel = null;
        StringBuilder retorno;

        try {
            retorno = new HttpService().execute(baseURL + "?key=" + key + "&lat=" + latitude + "&lon=" + longitude + "user_ip=remote").get();
            JSONObject reader = new JSONObject(retorno.toString());
            JSONObject results = reader.getJSONObject("results");
            cidadeModel = CidadeModel.readJSON(results);
            // cidadeModel = gson.fromJson(retorno.toString(), CidadeModel.class);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return cidadeModel;
    }

}

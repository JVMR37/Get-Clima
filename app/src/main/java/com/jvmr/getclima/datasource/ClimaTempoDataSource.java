package com.jvmr.getclima.datasource;

import com.google.gson.Gson;
import com.jvmr.getclima.model.CidadeModel;
import com.jvmr.getclima.service.HttpService;

import java.util.concurrent.ExecutionException;

public class ClimaTempoDataSource {
    final private String baseURL = "http://apiadvisor.climatempo.com.br/";
    final private String token = "d724e4adc0663de78a1f9caa7c207f3c";
    private String url = "";
    final private Gson gson = new Gson();

    private static ClimaTempoDataSource instance;

    public static ClimaTempoDataSource getInstance() {
        if (instance == null) {
            instance = new ClimaTempoDataSource();
        }
        return instance;
    }

    public CidadeModel buscarCidadePorId(String id) {
        CidadeModel cidadeModel = null;
        StringBuilder retorno;

        try {
            retorno = new HttpService().execute(baseURL + "api/v1/locale/city/" + id + "?token=" + token).get();
            cidadeModel = gson.fromJson(retorno.toString(), CidadeModel.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return cidadeModel;
    }


}

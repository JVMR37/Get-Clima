package com.jvmr.getclima.service;

import com.jvmr.getclima.model.CidadeModel;

public class PrevisaoTempoService {

    static private PrevisaoTempoService instance;

    private CidadeModel cidadeModel;

    public static PrevisaoTempoService getInstance() {
        if (instance == null) {
            instance = new PrevisaoTempoService();
        }
        return instance;
    }

    public CidadeModel getCidadeModel() {
        return cidadeModel;
    }

    public void setCidadeModel(CidadeModel cidadeModel) {
        this.cidadeModel = cidadeModel;
    }
}

package com.jvmr.getclima.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CidadeModel {
    private int id;
    private int temperatura;
    private String data;
    private String cod_condicao;
    private String descricao;
    private int umidade;
    private String velocidade_vento;
    private String slug_condicao;
    private String city;

    public CidadeModel(Integer temperatura, String data, String cod_condicao, String descricao, int umidade, String velocidade_vento, String slug_condicao, String city) {
        this.id = -1;
        this.temperatura = temperatura;
        this.data = data;
        this.cod_condicao = cod_condicao;
        this.descricao = descricao;
        this.umidade = umidade;
        this.velocidade_vento = velocidade_vento;
        this.slug_condicao = slug_condicao;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCod_condicao() {
        return cod_condicao;
    }

    public void setCod_condicao(String cod_condicao) {
        this.cod_condicao = cod_condicao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getUmidade() {
        return umidade;
    }

    public void setUmidade(int umidade) {
        this.umidade = umidade;
    }

    public String getVelocidade_vento() {
        return velocidade_vento;
    }

    public void setVelocidade_vento(String velocidade_vento) {
        this.velocidade_vento = velocidade_vento;
    }

    public String getSlug_condicao() {
        return slug_condicao;
    }

    public void setSlug_condicao(String slug_condicao) {
        this.slug_condicao = slug_condicao;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static CidadeModel readJSON(JSONObject json) throws JSONException {
        int temperatura = json.getInt("temp");
        String data = json.getString("date");
        String cod_condicao = json.getString("condition_code");
        String descricao = json.getString("description");
        int umidade = json.getInt("humidity");
        String velocidade_vento = json.getString("wind_speedy");
        String slug_condicao = json.getString("condition_slug");
        String city = json.getString("city");

        return new CidadeModel(temperatura, data, cod_condicao, descricao, umidade, velocidade_vento, slug_condicao, city);
    }
}

package com.jvmr.getclima.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Previsao {
    private String data;
    private int temp_max;
    private int temp_min;
    private String descricao;
    private String slug_condicao;

    public Previsao(String data, int temp_max, int temp_min, String descricao, String slug_condicao) {
        this.data = data;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.descricao = descricao;
        this.slug_condicao = slug_condicao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSlug_condicao() {
        return slug_condicao;
    }

    public void setSlug_condicao(String slug_condicao) {
        this.slug_condicao = slug_condicao;
    }

    public static Previsao readJSON(JSONObject json) throws JSONException {
        String data = json.getString("date");
        int t_max = json.getInt("max");
        int t_min = json.getInt("min");
        String descricao = json.getString("description");
        String slug_condicao = json.getString("condition");

        return new Previsao(data, t_max, t_min, descricao, slug_condicao);
    }
}

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
    private Previsao[] previsoes;

    public CidadeModel(Integer temperatura, String data, String cod_condicao, String descricao, int umidade, String velocidade_vento, String slug_condicao, String city, Previsao[] previsoes) {
        this.id = -1;
        this.temperatura = temperatura;
        this.data = data;
        this.cod_condicao = cod_condicao;
        this.descricao = descricao;
        this.umidade = umidade;
        this.velocidade_vento = velocidade_vento;
        this.slug_condicao = slug_condicao;
        this.city = city;
        this.previsoes = previsoes;
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

    public Previsao[] getPrevisoes() {
        return previsoes;
    }

    public void setPrevisoes(Previsao[] previsoes) {
        this.previsoes = previsoes;
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
        JSONArray jsonPrevisoes = json.getJSONArray("forecast");
        Previsao[] previsoes = new Previsao[jsonPrevisoes.length()];

        for (int i = 0; i < jsonPrevisoes.length(); i++) {
            JSONObject jsonPrevisao = jsonPrevisoes.getJSONObject(i);
            Previsao previsao = Previsao.readJSON(jsonPrevisao);
            previsoes[i] = previsao;
        }

        return new CidadeModel(temperatura, data, cod_condicao, descricao, umidade, velocidade_vento, slug_condicao, city, previsoes);
    }
}

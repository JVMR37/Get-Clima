package com.jvmr.getclima.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Previsao {
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

    Previsao(){}

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

    public static Previsao fromMap(Map<String, Object> previsaoMap) {
        return new Previsao(
                (String) previsaoMap.get("date"),
                Integer.parseInt(previsaoMap.get("max").toString()),
                Integer.parseInt(previsaoMap.get("min").toString()),
                previsaoMap.get("description").toString(),
                previsaoMap.get("condition").toString()
        );
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("date", data);
        map.put("max", temp_max);
        map.put("min", temp_min);
        map.put("description", descricao);
        map.put("condition", slug_condicao);

        return map;
    }
}

package com.jvmr.getclima.model;

import java.util.List;

public class UsuarioModel {
    private String id;
    private String nomeCompleto;
    private String email;
    private  String fotoPefilURL;
    private List<String> cidadesIds;

    public UsuarioModel(String id, String nomeCompleto, String email, List<String> cidadesIds) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.cidadesIds = cidadesIds;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getCidadesIds() {
        return cidadesIds;
    }

    public void setCidadesIds(List<String> cidadesIds) {
        this.cidadesIds = cidadesIds;
    }
}

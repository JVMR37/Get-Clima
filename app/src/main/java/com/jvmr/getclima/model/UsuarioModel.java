package com.jvmr.getclima.model;

import com.google.firebase.auth.FirebaseUser;
import com.jvmr.getclima.service.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioModel {
    private String nomeCompleto;
    private String email;
    private String fotoPerfilURL;
    private List<String> nomeCidadesList = new ArrayList<>();
    private FirebaseUser firebaseUser;

    public UsuarioModel(String nomeCompleto, String email, List<String> nomeCidadesList) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.nomeCidadesList = nomeCidadesList;
    }

    public UsuarioModel() {

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

    public List<String> getNomeCidadesList() {
        return nomeCidadesList;
    }

    public void addCidadeToList(CidadeModel novaCidade) {
        nomeCidadesList.add(novaCidade.getCity());
    }

    public void setNomeCidadesList(List<String> nomeCidadesList) {
        this.nomeCidadesList = nomeCidadesList;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> userMap = new HashMap<>();

        userMap.put("nomeCompleto", getNomeCompleto());
        userMap.put("email", getEmail());
        userMap.put("nomeCidadesList", getNomeCidadesList());

        return userMap;
    }

    public UsuarioModel fromMap(Map<String, Object> usuarioMap) {
        List<String> cidadesIds = new ArrayList<>();

        cidadesIds = Utils.convertObjectToListString(usuarioMap.get("nomeCidadesList"));

        UsuarioModel usuarioModel = new UsuarioModel(String.valueOf(usuarioMap.get("nomeCompleto")),
                String.valueOf(usuarioMap.get("email")),
                cidadesIds);

        return usuarioModel;
    }

    @Override
    public String toString() {
        return "UsuarioModel{" +
                "nomeCompleto='" + nomeCompleto + '\'' +
                ", email='" + email + '\'' +
                ", fotoPerfilURL='" + fotoPerfilURL + '\'' +
                ", nomeCidadesList=" + nomeCidadesList +
                ", firebaseUser=" + firebaseUser +
                '}';
    }
}

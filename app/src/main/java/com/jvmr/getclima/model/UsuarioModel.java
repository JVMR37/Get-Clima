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
    private List<String> cidadesIds = new ArrayList<>();
    private FirebaseUser firebaseUser;

    public UsuarioModel(String nomeCompleto, String email, List<String> cidadesIds) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.cidadesIds = cidadesIds;
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

    public List<String> getCidadesIds() {
        return cidadesIds;
    }

    public void setCidadesIds(List<String> cidadesIds) {
        this.cidadesIds = cidadesIds;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> userMap = new HashMap<>();

        userMap.put("nomeCompleto", getNomeCompleto());
        userMap.put("email", getEmail());
        userMap.put("cidadesIds", getCidadesIds());

        return userMap;
    }

    public UsuarioModel fromMap(Map<String, Object> usuarioMap) {
        List<String> cidadesIds = new ArrayList<>();

        cidadesIds = Utils.convertObjectToListString(usuarioMap.get("cidadesIds"));

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
                ", cidadesIds=" + cidadesIds +
                ", firebaseUser=" + firebaseUser +
                '}';
    }
}

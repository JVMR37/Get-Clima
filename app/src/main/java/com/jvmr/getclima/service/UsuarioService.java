package com.jvmr.getclima.service;

import com.jvmr.getclima.model.UsuarioModel;

public class UsuarioService {

    static private UsuarioService instance;

    private UsuarioModel usuarioModel;

    public static UsuarioService getInstance() {
        if (instance == null) {
            instance = new UsuarioService();
        }
        return instance;
    }

    public UsuarioModel getUsuarioModel() {
        return usuarioModel;
    }

    public void setUsuarioModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }
}
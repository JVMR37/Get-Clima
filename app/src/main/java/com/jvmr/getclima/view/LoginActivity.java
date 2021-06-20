package com.jvmr.getclima.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jvmr.getclima.R;
import com.jvmr.getclima.datasource.HGDataSource;
import com.jvmr.getclima.model.CidadeModel;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);//hash

        HGDataSource api = HGDataSource.getInstance();
        CidadeModel cidadeModel = api.buscarCidadePorGeoLoc(-20.4435f, -54.6478f);
        if (cidadeModel != null)
            System.out.println(cidadeModel.toString());
    }

    public void logar(View view) {
        String email, senha;//hash senha
        email = edtEmail.getText().toString();
        senha = edtSenha.getText().toString();

        //email -> salvar no banco
        //senha -> salvar no banco

        //Intent it = new Intent(LoginActivity.this, PrincipalActivity.class);// --> leva para a tela principal
        //startActivity(it);
    }
}
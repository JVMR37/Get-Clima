package com.jvmr.getclima.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jvmr.getclima.R;

public class PerfilActivity extends AppCompatActivity {
    private EditText edtNome, edtNovoEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        this.setTitle("Meus dados");

        edtNome = findViewById(R.id.edtNome);
        edtNovoEmail = findViewById(R.id.edtNovoEmail);
    }

    public void salvarDados(View view) {
        String nome, email;
        nome = edtNome.getText().toString();
        email = edtNovoEmail.getText().toString();

        //nome -> salvar no banco
        //email -> salvar no banco

        //Intent it = new Intent(PerfilActivity.this, PrincipalActivity.class);// --> leva de volta pra tela principal
        //startActivity(it);
    }
}
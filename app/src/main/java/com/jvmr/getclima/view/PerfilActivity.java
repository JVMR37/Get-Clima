package com.jvmr.getclima.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.jvmr.getclima.R;

public class PerfilActivity extends AppCompatActivity {
    private TextInputLayout edtNome, edtNovoEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        edtNome = findViewById(R.id.edtNome);
        edtNovoEmail = findViewById(R.id.edtNovoEmail);
    }

    public void salvarDados(View view) {
        String nome, email;
        nome = edtNome.getEditText().toString();
        email = edtNovoEmail.getEditText().toString();

        //nome -> salvar no banco
        //email -> salvar no banco

        //Intent it = new Intent(PerfilActivity.this, PrincipalActivity.class);// --> leva de volta pra tela principal
        //startActivity(it);
    }
}
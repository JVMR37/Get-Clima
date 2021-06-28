package com.jvmr.getclima.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvmr.getclima.R;
import com.jvmr.getclima.datasource.HGDataSource;
import com.jvmr.getclima.model.CidadeModel;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout edtEmail;
    private TextInputLayout edtSenha;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);//hash

        mAuth = FirebaseAuth.getInstance();

        HGDataSource api = HGDataSource.getInstance();
        CidadeModel cidadeModel = api.buscarCidadePorGeoLoc(-20.4435f, -54.6478f);
        if (cidadeModel != null)
            System.out.println(cidadeModel.toString());
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(it);
        }
    }

    public void logar(View view) {
        String email, senha;
        email = edtEmail.getEditText().getText().toString();
        senha = edtSenha.getEditText().getText().toString();

        if(email.equals("") && senha.equals("")){
            Toast.makeText(LoginActivity.this, "É necessário preencher todos os campos",
                    Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(LoginActivity.this, "Seja Bem-Vindo ao Get Clima :)",
                                    Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(LoginActivity.this, MainActivity.class);// --> leva para a tela principal
                            startActivity(it);

                        } else {
                            Toast.makeText(LoginActivity.this, "Não foi possível logar com esse usuário :(",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void abrirCadastro(View view) {
        Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(it);
    }

    public void testar(View view) {
        Intent it = new Intent(LoginActivity.this, MainActivity.class);// --> leva para a tela principal
        startActivity(it);
    }
}
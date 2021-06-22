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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvmr.getclima.R;
import com.jvmr.getclima.datasource.HGDataSource;
import com.jvmr.getclima.model.CidadeModel;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtSenha;
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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //TODO: Encaminhar para Tela Principal
        }
    }

    public void logar(View view) {
        String email, senha;//hash senha
        email = edtEmail.getText().toString();
        senha = edtSenha.getText().toString();

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(LoginActivity.this, "Seja Bem-Vindo ao Get Clima : )",
                                    Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(LoginActivity.this, MainActivity.class);// --> leva para a tela principal
                            startActivity(it);

                        } else {
                            Toast.makeText(LoginActivity.this, "Não foi possível logar com esse usuário : (",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void abrirCadastro(View view){
        Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(it);
    }
}
package com.jvmr.getclima.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvmr.getclima.R;

public class LoginActivity extends AppCompatActivity {
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSED_REQUEST_CODE = 1234;

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

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        }
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

        //Tratamento caso o usuário tente logar e os campos estiverem vazios

        if(email.equals("") && senha.equals("")){
            Toast.makeText(LoginActivity.this, "É necessário preencher todos os campos!",
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

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, permissions,
                        LOCATION_PERMISSED_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSED_REQUEST_CODE);
        }
    }
}
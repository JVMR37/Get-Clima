package com.jvmr.getclima.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvmr.getclima.R;
import com.jvmr.getclima.model.UsuarioModel;

public class CadastroActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    UsuarioModel usuarioModel;
    private EditText edtNome, edtEmail, edtSenha, edtConfirmaSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setTitle("Cadastro");

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);//hash
        edtConfirmaSenha = findViewById(R.id.edtConfirmaSenha);//hash

        usuarioModel = new UsuarioModel();
        mAuth = FirebaseAuth.getInstance();
    }

    public void cadastrarUsuario(String password) {// Acho que náo precisa receber password como parametro !!
        mAuth.createUserWithEmailAndPassword(usuarioModel.getEmail(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FirebaseAuth", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FirebaseAuth", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroActivity.this, "Nao foi possível realizar o Cadastro : (",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });

    }
}
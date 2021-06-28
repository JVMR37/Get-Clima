package com.jvmr.getclima.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jvmr.getclima.R;
import com.jvmr.getclima.model.UsuarioModel;

public class CadastroActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    UsuarioModel usuarioModel;
    private TextInputLayout edtNome, edtEmail, edtSenha, edtConfirmaSenha;
    FirebaseFirestore db;
    Button btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtNovoEmail);
        edtSenha = findViewById(R.id.edtSenha);//hash
        edtConfirmaSenha = findViewById(R.id.edtConfirmaSenha);//hash
        btnCadastrar = findViewById(R.id.btnSalvarMudancas);

        usuarioModel = new UsuarioModel();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnCadastrar.setOnClickListener(
                v -> cadastrarUsuario()
        );
    }

    public void cadastrarUsuario() {
        String password = edtSenha.getEditText().getText().toString();
        usuarioModel.setEmail(edtEmail.getEditText().getText().toString());
        usuarioModel.setNomeCompleto(edtNome.getEditText().getText().toString());

        //Tratamento caso o usuário tente criar uma senha com menos de 6 caracteres
        if(password.length()<6){
            Toast.makeText(CadastroActivity.this, "A senha precisa ter no minímo 6 caracteres",
                    Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(usuarioModel.getEmail(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("FirebaseAuth", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            db.collection("users")
                                    .document(user.getUid())
                                    .set(usuarioModel.toMap())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso :)",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CadastroActivity.this,
                                                    "Conta criada com sucesso, porém não foi possível salvar as informações do usuário :(",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FirebaseAuth", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroActivity.this, "Nao foi possível realizar o Cadastro :(",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });

    }
}
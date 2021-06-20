package com.jvmr.getclima.datasource;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvmr.getclima.model.UsuarioModel;

public class FirebaseAuthDatasource extends ViewModel {

    private static FirebaseAuthDatasource instance;
    static private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    public static FirebaseAuthDatasource getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthDatasource();
            mAuth = FirebaseAuth.getInstance();
        }
        return instance;
    }

    public boolean checarSeOUsuarioEstaLogado() {
        currentUser = mAuth.getCurrentUser();

        return currentUser != null;
    }

    public boolean registrarUsuario(UsuarioModel usuarioModel, String password) {
        return mAuth.createUserWithEmailAndPassword(usuarioModel.getEmail(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });

    }
}

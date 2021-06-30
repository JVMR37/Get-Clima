package com.jvmr.getclima.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jvmr.getclima.R;
import com.jvmr.getclima.model.UsuarioModel;
import com.jvmr.getclima.service.UsuarioService;


public class PerfilFragment extends Fragment {
    private TextInputLayout edtNome, edtEmail;
    private Button btnSalvar;
    private UsuarioService userInstance;
    private FirebaseAuth fbAuth;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtNome = view.findViewById(R.id.edtNovoNome);
        edtEmail = view.findViewById(R.id.edtNovoEmail);
        btnSalvar = view.findViewById(R.id.btnSalvarMudancas);

        userInstance = UsuarioService.getInstance();

        fbAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarMudancas();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        edtNome.getEditText().setText(userInstance.getUsuarioModel().getNomeCompleto());
        edtEmail.getEditText().setText(userInstance.getUsuarioModel().getEmail());
    }

    public void salvarMudancas(){
        String nome, email;
        nome = edtNome.getEditText().getText().toString();
        email = edtEmail.getEditText().getText().toString();

        if(nome.equals("") && email.equals("")){
            Toast toast = Toast.makeText(getActivity(), "É necessário preencher todos os campos", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            UsuarioModel usuarioModel = userInstance.getUsuarioModel();
            usuarioModel.setNomeCompleto(nome);
            usuarioModel.setEmail(email);

            FirebaseUser user = fbAuth.getCurrentUser();
            assert user != null;
            db.collection("users")
                    .document(user.getUid())
                    .set(usuarioModel.toMap())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Alterações salvas com sucesso",
                                    Toast.LENGTH_LONG).show();
                            Intent it = new Intent(getContext(), MainActivity.class);// --> leva para a tela principal
                            startActivity(it);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),
                                    "Não foi possível salvar as alterações",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
package com.jvmr.getclima.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.jvmr.getclima.R;


public class PerfilFragment extends Fragment {
    private TextInputLayout edtNome, edtEmail;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        //edtNome.getEditText().setText(USUARIO.NOME);
        //edtEmail.getEditText().setText(USUARIO.EMAIL);
    }

    public void salvarMudancas(){
        String nome, email;
        nome = edtNome.getEditText().toString();
        email = edtEmail.getEditText().toString();

        if(nome.equals("") && email.equals("")){
            Toast toast = Toast.makeText(getActivity(), "É necessário preencher todos os campos", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            //TODO: salvar no banco as alteraçãoes
        }
    }
}
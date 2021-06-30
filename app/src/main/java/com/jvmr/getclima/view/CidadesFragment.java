package com.jvmr.getclima.view;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jvmr.getclima.R;
import com.jvmr.getclima.model.CidadeModel;
import com.jvmr.getclima.model.UsuarioModel;
import com.jvmr.getclima.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;


public class CidadesFragment extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth fbAuth;
    private Spinner spnrCidades, spnrEstados;
    private ListView lvCidadesAdd;
    private TextView txtAddCidade;
    private ImageButton ibtnAddCidade;
    private String novaCidade;
    private UsuarioService userInstance;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cidades, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spnrCidades = (Spinner) view.findViewById(R.id.spnrCidades);
        spnrEstados = (Spinner) view.findViewById(R.id.spnrEstados);
        lvCidadesAdd = (ListView) view.findViewById(R.id.lvCidades);
        txtAddCidade = view.findViewById(R.id.txtAddCidade);
        ibtnAddCidade = view.findViewById(R.id.ibtnAddCidade);
        userInstance = UsuarioService.getInstance();

        fbAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ibtnAddCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCidade();
            }
        });

        spnrEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String estadoSelecionado =  spnrEstados.getSelectedItem().toString();
                List<String> cidades = CidadeModel.getCidadesPorEstado(estadoSelecionado);

                ArrayAdapter<String> adpt_cidades = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cidades);
                spnrCidades.setAdapter(adpt_cidades);
                novaCidade = spnrCidades.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selecaoEstados();
            }
        });
        spnrCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                novaCidade = spnrCidades.getSelectedItem().toString();
                txtAddCidade.setText(novaCidade);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txtAddCidade.setText("Escolha uma cidade");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        cidadesAdicionadas();
        selecaoEstados();
    }


    public void selecaoEstados(){
        List<String> estados = CidadeModel.getEstados();
        ArrayAdapter<String> adpt_estados = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, estados);
        spnrEstados.setAdapter(adpt_estados);
    }


    public void cidadesAdicionadas(){
        List <String> cidadesAdd = new ArrayList<>();
        cidadesAdd = userInstance.getUsuarioModel().getCidadesIds();
        if(cidadesAdd.isEmpty()){
            Toast.makeText(getActivity(), "Você ainda não tem cidades adicionadas!!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        CostumeArrayAdapter adpt = new CostumeArrayAdapter(requireActivity(), cidadesAdd);
        lvCidadesAdd.setAdapter(adpt);
    }

    public void addCidade(){
        UsuarioModel usuarioModel = userInstance.getUsuarioModel();
        List <String> cidadesAdd =usuarioModel.getCidadesIds();

        cidadesAdd.add(novaCidade);
        usuarioModel.setCidadesIds(cidadesAdd);

        FirebaseUser user = fbAuth.getCurrentUser();
        assert user != null;
        db.collection("users")
                .document(user.getUid())
                .set(usuarioModel.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        cidadesAdicionadas();
                        Toast.makeText(getActivity(), "Cidade adicionada com sucesso :)",
                                Toast.LENGTH_LONG).show();
                        txtAddCidade.setText("Escolha uma cidade");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),
                                "Não foi possível adiconar a cidade :(",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void selecionaCidade(){
        lvCidadesAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Consulta api e passa infos para InicioFragment
            }
        });
    }

    public void deletaCidade(){
        CostumeArrayAdapter.ViewHolder vh = new CostumeArrayAdapter.ViewHolder();
        CostumeArrayAdapter.getIcon(vh, getView()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),
                        "TESTANDOOOO",
                        Toast.LENGTH_SHORT).show();
            }
        });

        UsuarioService userInstance = UsuarioService.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();

        List<String> cidades = userInstance.getUsuarioModel().getCidadesIds();

    }


}

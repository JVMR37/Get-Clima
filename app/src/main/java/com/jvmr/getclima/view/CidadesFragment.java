package com.jvmr.getclima.view;


import android.annotation.SuppressLint;
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
import com.jvmr.getclima.datasource.HGDataSource;
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
    private String novaCidade = "", estado = "";
    private UsuarioService userInstance;
    private HGDataSource hg;
    private List <String> cidadesAdd;

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
        lvCidadesAdd = view.findViewById(R.id.lvCidades);
        txtAddCidade = view.findViewById(R.id.txtAddCidade);
        ibtnAddCidade = view.findViewById(R.id.ibtnAddCidade);
        userInstance = UsuarioService.getInstance();

        fbAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        hg = HGDataSource.getInstance();

        cidadesAdd = new ArrayList<>();
        cidadesAdd = userInstance.getUsuarioModel().getNomeCidadesList();

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                estado = "";
                selecaoEstados();
            }
        });
        spnrCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                novaCidade = spnrCidades.getSelectedItem().toString();
                estado = spnrEstados.getSelectedItem().toString();
                txtAddCidade.setText(novaCidade);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                novaCidade = "";
                estado = "";
                txtAddCidade.setText(R.string.escolha);
            }
        });

        lvCidadesAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("-------------------> "+cidadesAdd.get(position));
                //TODO: consulta api
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
        if(cidadesAdd.isEmpty()){
            Toast.makeText(getActivity(), "Você ainda não tem cidades adicionadas!!",
                    Toast.LENGTH_LONG).show();
        }

        else {
            CostumeArrayAdapter adpt = new CostumeArrayAdapter(requireActivity(), cidadesAdd);
            adpt.setAdpt(adpt);
            lvCidadesAdd.setAdapter(adpt);
        }
    }

    public void addCidade(){
        UsuarioModel usuarioModel = userInstance.getUsuarioModel();

        if(novaCidade.equals("")){
            Toast.makeText(getActivity(), "Selecione uma cidade",
                    Toast.LENGTH_SHORT).show();
        }

        else if(cidadesAdd.contains(novaCidade)){
            Toast.makeText(getActivity(), "Cidade já adicionada",
                    Toast.LENGTH_SHORT).show();
            novaCidade = "";
            estado = "";
        }

        else{
            usuarioModel.addCidadeToList(novaCidade);

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
                                    Toast.LENGTH_SHORT).show();
                            txtAddCidade.setText(R.string.escolha);
                            novaCidade = "";

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

    }

    public static void removeCidade(CostumeArrayAdapter adpt, String cidade, List<String> cidades){
        //System.out.println("---------------> "+ cidade);
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = fbAuth.getCurrentUser();
        UsuarioService userInstance = UsuarioService.getInstance();
        UsuarioModel usuarioModel = userInstance.getUsuarioModel();

        usuarioModel.removeCidades(cidade);
        assert user != null;
        db.collection("users")
                .document(user.getUid())
                .set(usuarioModel.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        adpt.notifyDataSetChanged();
                        Toast.makeText(adpt.getContext(), "Cidade removida com sucesso :)",
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(adpt.getContext(),
                                "Não foi possível remover a cidade :(",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }


}

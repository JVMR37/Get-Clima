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

import com.google.firebase.auth.FirebaseUser;
import com.jvmr.getclima.R;
import com.jvmr.getclima.model.CidadeModel;

import java.util.List;


public class CidadesFragment extends Fragment {
    private Spinner spnrCidades, spnrEstados;
    private ListView lvCidadesAdd;
    private TextView txtAddCidade;
    private ImageButton ibtnAddCidade;
    private String novaCidade;


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
                //====Popula o spinner de estados===
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
        //TODO: recupera as cidades adicionadas do usuário

        //recupera do banco as cidades ja adicionadas pelo usuário

        //Tratamento caso o usu[ario ainda náo tenha cidades adicionadas
        /**if(cidades.isNull()){
            Toast.makeText(getActivity(), "Você ainda não tem cidades adicionadas!!",
                    Toast.LENGTH_LONG).show();
            return;
        }**/

        //====Popula o listView====
        //CostumeArrayAdapter adpt = new CostumeArrayAdapter(requireActivity(), cidadesAdd);
        //lvCidadesAdd.setAdapter(adpt);
    }

    public void addCidade(){
        //TODO: adiciona ao banco a cidade adicionada

        //verifica se a cidade a ser adicionada já esta na lista de cidades do usuário

        //adicona cidades no banco do usário
        Toast.makeText(getActivity(), "Cidade adicionada com sucesso!!",
                Toast.LENGTH_LONG).show();
        txtAddCidade.setText("Escolha uma cidade");
    }


}

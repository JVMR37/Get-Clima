package com.jvmr.getclima.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jvmr.getclima.R;

import java.util.ArrayList;
import java.util.List;


public class CidadesFragment extends Fragment {
    Spinner spnrCidades, spnrEstados;
    ListView lvCidadesAdd;


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

    }

    @Override
    public void onResume() {
        super.onResume();
        selecaoEstados();
        cidadesAdicionadas();
    }


    public void selecaoEstados(){
        //TODO: recuperar os estados dísponíveis

        //====Popula o spinner de cidades===
        //adpt_estados = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, estados);
        //spnrEstados.setAdapter(adpt_estados);
    }

    public void selecaoCidades(){
        //TODO: recupera cidades disponíveis a partir do estado selecionado

        //====Popula o spinner de estados===
        //ArrayAdapter<String> adpt_cidades = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, cidades);
        //spnrCidades.setAdapter(adpt_cidades);

    }

    public void cidadesAdicionadas(){
        //TODO: recupera as cidades adicionadas do usuário

        //====Popula o listView====
        //CostumeArrayAdapter adpt = new CostumeArrayAdapter(requireActivity(), cidadesAdd);
        //lvCidadesAdd.setAdapter(adpt);
    }

}

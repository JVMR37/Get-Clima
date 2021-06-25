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
    Spinner spnrCidades;
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
        lvCidadesAdd = (ListView) view.findViewById(R.id.lvCidades);

    }

    @Override
    public void onResume() {
        super.onResume();
        selecaoCidades();
        cidadesAdicionadas();
    }


    public void selecaoCidades(){
        //TODO: recuperar as cidades disponíveis para escolher - cidadesDispo

        //====Popula o spinner de cidades===
        //ArrayAdapter adpt_cidades = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, cidadesDispo);
        //adpt_cidades.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //spnrCidades.setAdapter(adpt_cidades);
    }

    public void cidadesAdicionadas(){
        //TODO: recupera as cidades adicionadas do usuário - cidadesAdd

        //====Popula o listView====
        //CostumeArrayAdapter adpt = new CostumeArrayAdapter(requireActivity(), cidadesAdd);
        //lvCidadesAdd.setAdapter(adpt);
    }

}

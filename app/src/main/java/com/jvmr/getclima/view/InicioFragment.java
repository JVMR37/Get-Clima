package com.jvmr.getclima.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jvmr.getclima.R;
import com.jvmr.getclima.datasource.HGDataSource;
import com.jvmr.getclima.model.CidadeModel;
import com.jvmr.getclima.model.PrevisaoModel;
import com.jvmr.getclima.model.UsuarioModel;
import com.jvmr.getclima.service.PrevisaoTempoService;
import com.jvmr.getclima.service.UsuarioService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class InicioFragment extends Fragment {
    private ImageView img;
    private PrevisaoTempoService previsaoInstance;
    private UsuarioService usuarioService;
    private CidadeModel cidadeModel;
    private TextView cidade, condicao, temp, min, max, nascerSol
            , porSol, umidade, vento, data1, min1, max1, data2, min2, max2;
    private int position;
    private HGDataSource hg;
    private List<String> cidades;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        usuarioService = UsuarioService.getInstance();
        previsaoInstance = PrevisaoTempoService.getInstance();
        cidadeModel = previsaoInstance.getCidadeModel();

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cidade = view.findViewById(R.id.txtCidade);
        condicao = view.findViewById(R.id.txtCondicao);
        temp = view.findViewById(R.id.txtTemp);
        min = view.findViewById(R.id.txtMinimo);
        max = view.findViewById(R.id.txtMaximo);
        nascerSol = view.findViewById(R.id.txtNascerDoSol);
        porSol = view.findViewById(R.id.txtPorDoSol);
        umidade = view.findViewById(R.id.txtUmidade);
        vento = view.findViewById(R.id.txtVento);
        data1 = view.findViewById(R.id.txtData1);
        min1 = view.findViewById(R.id.txtMinimo1);
        max1 = view.findViewById(R.id.txtMaximo1);
        data2 = view.findViewById(R.id.txtData2);
        min2 = view.findViewById(R.id.txtMinimo2);
        max2 = view.findViewById(R.id.txtMaximo2);
        img = view.findViewById(R.id.imgCondicao);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(cidadeModel != null){
            setInfo();

        }
        else{

            System.out.println("ENTREI2222222222==============================");
            cidadeDefault();
        }
    }

    @SuppressLint("SetTextI18n")
    public void setInfo(){
        int hoje = getData();
        cidades = usuarioService.getUsuarioModel().getNomeCidadesList();
        position = cidadeModel.getPos();

        if(position == -1){
            cidade.setText(cidadeModel.getCityName());
        }
        else {
            cidade.setText(cidades.get(position).split(",\\s")[0]);
        }

        condicao.setText(cidadeModel.getDescricao());
        temp.setText(Integer.toString(cidadeModel.getTemperatura()));
        min.setText(Integer.toString(cidadeModel.getPrevisoes().get(hoje).getTemp_min()));
        max.setText(Integer.toString(cidadeModel.getPrevisoes().get(hoje).getTemp_max()));
        nascerSol.setText(cidadeModel.getSunrise());
        porSol.setText(cidadeModel.getSunset());
        umidade.setText(Integer.toString(cidadeModel.getUmidade()));
        vento.setText(cidadeModel.getVelocidade_vento());
        data1.setText(cidadeModel.getPrevisoes().get(hoje+1).getData());
        min1.setText(Integer.toString(cidadeModel.getPrevisoes().get(hoje+1).getTemp_min()));
        max1.setText(Integer.toString(cidadeModel.getPrevisoes().get(hoje+1).getTemp_max()));
        data2.setText(cidadeModel.getPrevisoes().get(hoje+2).getData());
        min2.setText(Integer.toString(cidadeModel.getPrevisoes().get(hoje+2).getTemp_min()));
        max2.setText(Integer.toString(cidadeModel.getPrevisoes().get(hoje+2).getTemp_max()));

        setIcon();

    }

    public int getData(){
        List<PrevisaoModel> previsoes = cidadeModel.getPrevisoes();

        Locale loc = new Locale("pt", "BR");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM", loc);
        Date date = new Date();
        String hoje = formatter.format(date);
        for (int i = 0; i < previsoes.size(); i++) {

            if(hoje.equals(previsoes.get(i).getData())){
                System.out.println("---------------DATE-------------->"+i);
                return i;
            }
        }
        return -1;
    }

    public void setIcon(){
        String condicao = cidadeModel.getSlug_condicao();
        switch (condicao) {
            case "storm":
                img.setImageResource(R.drawable.storm);
                break;
            case "snow":
                img.setImageResource(R.drawable.snow);
                break;
            case "hail":
                img.setImageResource(R.drawable.hail);
                break;
            case "rain":
                img.setImageResource(R.drawable.rain);
                break;
            case "fog":
                img.setImageResource(R.drawable.fog);
                break;
            case "clear_day":
                img.setImageResource(R.drawable.clear_day);
                break;
            case "clear_night":
                img.setImageResource(R.drawable.clear_night);
                break;
            case "cloud":
                img.setImageResource(R.drawable.cloud);
                break;
            case "cloudly_day":
                img.setImageResource(R.drawable.cloudly_day);
                break;
            case "cloudly_night":
                img.setImageResource(R.drawable.cloudly_night);
                break;
            case "none_day":
                img.setImageResource(R.drawable.none_day);
                break;
            case "none_night":
                img.setImageResource(R.drawable.none_night);
                break;
        }

    }

    public void cidadeDefault(){
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = fbAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        hg = HGDataSource.getInstance();
        previsaoInstance = PrevisaoTempoService.getInstance();

//        db.collection("weatherForecasts")







        db.collection("users").document(fbUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UsuarioModel user = documentSnapshot.toObject(UsuarioModel.class);
                assert user != null;
                List<String> cidadesUF  = user.getNomeCidadesList();
                String [] cidadeEstado = cidadesUF.get(0).split(",\\s");
                //System.out.println("----------------------->"+cidadesUF);

                CidadeModel cidadeDefault = hg.buscarCidadePorNomeEstado(cidadeEstado[0], cidadeEstado[1]);
                System.out.println("----------DEFAULTT------------->"+cidadeDefault);
                position = -1;
                cidadeDefault.setPos(position);

                cidadeModel= cidadeDefault;
                cidadeModel.setCityName(cidadeEstado[0]);

                usuarioService.setUsuarioModel(user);
                previsaoInstance.setCidadeModel(cidadeDefault);

                setInfo();
            }
        });
    }


}
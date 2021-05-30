package com.jvmr.getclima.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jvmr.getclima.R;
import com.jvmr.getclima.datasource.ClimaTempoDataSource;
import com.jvmr.getclima.model.CidadeModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClimaTempoDataSource climaTempoDataSource = ClimaTempoDataSource.getInstance();

        CidadeModel cidadeModel = climaTempoDataSource.buscarCidadePorId("3477");
        if (cidadeModel != null)
            System.out.println(cidadeModel.toString());
    }
}
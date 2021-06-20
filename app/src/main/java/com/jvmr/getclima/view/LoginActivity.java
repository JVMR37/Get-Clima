package com.jvmr.getclima.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jvmr.getclima.R;
import com.jvmr.getclima.datasource.HGDataSource;
import com.jvmr.getclima.model.CidadeModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HGDataSource api = HGDataSource.getInstance();

        CidadeModel cidadeModel = api.buscarCidadePorGeoLoc(-20.4435f, -54.6478f);
        if (cidadeModel != null)
            System.out.println(cidadeModel.toString());
    }
}
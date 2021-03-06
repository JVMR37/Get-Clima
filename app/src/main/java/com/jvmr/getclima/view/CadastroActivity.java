package com.jvmr.getclima.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jvmr.getclima.R;
import com.jvmr.getclima.datasource.HGDataSource;
import com.jvmr.getclima.model.CidadeModel;
import com.jvmr.getclima.model.UsuarioModel;
import com.jvmr.getclima.service.PrevisaoTempoService;
import com.jvmr.getclima.service.Utils;

import java.io.IOException;
import java.util.List;

public class CadastroActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private UsuarioModel usuarioModel;
    private TextInputLayout edtNome, edtEmail, edtSenha, edtConfirmaSenha;
    private FirebaseFirestore db;
    private Button btnCadastrar;
    private PrevisaoTempoService previsaoInstance;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSED_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionGranted = false;

    private FusedLocationProviderClient fusedLocationClient;
    CidadeModel cidadeAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        HGDataSource api = HGDataSource.getInstance();
        previsaoInstance = PrevisaoTempoService.getInstance();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        }

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtNovoEmail);
        edtSenha = findViewById(R.id.edtSenha);//hash
        edtConfirmaSenha = findViewById(R.id.edtConfirmaSenha);//hash
        btnCadastrar = findViewById(R.id.btnSalvarMudancas);

        usuarioModel = new UsuarioModel();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnCadastrar.setOnClickListener(
                v -> cadastrarUsuario()
        );

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            cidadeAtual = api.buscarCidadePorGeoLoc(location.getLatitude(), location.getLongitude());
                            //System.out.println("------------CIDADEATUAL--------->"+cidadeAtual);

                            usuarioModel.addCidadeToList(cidadeAtual.getCity());
                            //System.out.println("------------CIDADEATUAL--------->"+usuarioModel);
                            saveWeatherForecastForCurrentCity(cidadeAtual);
                            previsaoInstance.setCidadeModel(cidadeAtual);
                        }
                    }
                });
    }

    public void cadastrarUsuario() {
        String password = edtSenha.getEditText().getText().toString();
        String email = edtEmail.getEditText().getText().toString();
        String nome = edtNome.getEditText().getText().toString();
        usuarioModel.setEmail(email);
        usuarioModel.setNomeCompleto(nome);

        //Tratamento caso o usu??rio tente salvar sem preencher os campos
        if(email.equals("") || nome.equals("") || password.equals("")){
            Toast.makeText(CadastroActivity.this, "?? necess??rio preencher todos os campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //Tratamento caso o usu??rio tente criar uma senha com menos de 6 caracteres
        if(password.length()<6){
            Toast.makeText(CadastroActivity.this, "A senha precisa ter no m??nimo 6 caracteres",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(usuarioModel.getEmail(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("FirebaseAuth", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            db.collection("users")
                                    .document(user.getUid())
                                    .set(usuarioModel.toMap())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso :)",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent it = new Intent(CadastroActivity.this, MainActivity.class);// --> leva para a tela principal
                                            startActivity(it);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CadastroActivity.this,
                                                    "Conta criada com sucesso, por??m n??o foi poss??vel salvar as informa????es do usu??rio :(",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            Log.w("FirebaseAuth", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroActivity.this, "Nao foi poss??vel realizar o Cadastro :(",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions,
                        LOCATION_PERMISSED_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSED_REQUEST_CODE);
        }
    }

    public void saveWeatherForecastForCurrentCity(CidadeModel cidadeAtual) {
        String date = Utils.getDateNow();

        db.collection("weatherForecasts")
                .document(date + "-" + cidadeAtual.getCity())
                .set(cidadeAtual.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        System.out.println("===============================");
//                        System.out.println("Deu certo l?? bro");
//                        System.out.println("===============================");
                    }
                });
    }
}
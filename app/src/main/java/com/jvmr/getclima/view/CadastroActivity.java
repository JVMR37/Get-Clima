package com.jvmr.getclima.view;

import android.Manifest;
import android.content.pm.PackageManager;
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
import com.jvmr.getclima.service.Utils;

public class CadastroActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    UsuarioModel usuarioModel;
    private TextInputLayout edtNome, edtEmail, edtSenha, edtConfirmaSenha;
    FirebaseFirestore db;
    Button btnCadastrar;

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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            System.out.println("====================================");
                            System.out.println(location.toString());
                            cidadeAtual = api.buscarCidadePorGeoLoc(location.getLatitude(), location.getLongitude());
                            System.out.println(cidadeAtual.toString());
                            saveWeatherForecastForCurrentCity(cidadeAtual);
                            System.out.println("====================================");

                        }
                    }
                });

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
    }

    public void cadastrarUsuario() {
        String password = edtSenha.getEditText().getText().toString();
        usuarioModel.setEmail(edtEmail.getEditText().getText().toString());
        usuarioModel.setNomeCompleto(edtNome.getEditText().getText().toString());

        //Tratamento caso o usuário tente criar uma senha com menos de 6 caracteres
        if (password.length() < 6) {
            Toast.makeText(CadastroActivity.this, "A senha precisa ter no minímo 6 caracteres",
                    Toast.LENGTH_LONG).show();
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

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CadastroActivity.this,
                                                    "Conta criada com sucesso, porém não foi possível salvar as informações do usuário :(",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FirebaseAuth", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroActivity.this, "Nao foi possível realizar o Cadastro :(",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
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
                .document(date + cidadeAtual.getCity())
                .set(cidadeAtual.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("===============================");
                        System.out.println("Deu certo lá bro");
                        System.out.println("===============================");
                    }
                });
    }
}
package com.app.ctrhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registrarse extends AppCompatActivity {
    private static final String TAG = "CtrHistory";
    private EditText Nombre, Apellidos, Estaca_Districto, Barrio_Rama, Email, Password;
    private Button Ingresar;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Nombre = (EditText) findViewById(R.id.Nombre);
        Apellidos = (EditText) findViewById(R.id.Apellidos);
        Estaca_Districto = (EditText) findViewById(R.id.Estaca_Districto);
        Barrio_Rama = (EditText) findViewById(R.id.Barrio_Rama);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        Ingresar = findViewById(R.id.Ingresar);
        mAuth = FirebaseAuth.getInstance();

        final String nombre = Nombre.getText().toString();
        final String apellido = Apellidos.getText().toString();
        final String estacaD = Estaca_Districto.getText().toString();
        final String barrioR = Barrio_Rama.getText().toString();
        final String email = Email.getText().toString();
        final String password = Password.getText().toString();

        if (validar(nombre,estacaD,barrioR,apellido,password,email)) {
            Ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cargardatos(nombre, estacaD, barrioR, apellido, password, email);
                    registrarse();
                    Intent intent = new Intent(Registrarse.this, Inicio.class);
                    startActivity(intent);

                }
            });


        }
    }


    public boolean validar(String nombre,String estacaD, String barrioR, String apellido, String rpassword, String remail)
    {

        boolean retorno = true;



        if (nombre.isEmpty())
        {
            Nombre.setError("campo vacio");
            retorno = false;

        }
        if (apellido.isEmpty())
        {
            Apellidos.setError("campo vacio");
            retorno = false;
        }
        if (estacaD.isEmpty())
        {
            Estaca_Districto.setError("campo vacio");
            retorno = false;
        }
        if (barrioR.isEmpty())
        {
            Barrio_Rama.setError("campo vacio");
            retorno = false;
        }
        if (remail.isEmpty())
        {
            Email.setError("campo vacio");
            retorno = false;
        }
        if (rpassword.isEmpty())
        {
            Password.setError("campo vacio");
            retorno = false;
        }

        return retorno;
    }
    private void cargardatos(String nombre,String estacaD, String barrioR, String apellido, String rpassword, String remail) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Map<String, Object> datosUsuario= new HashMap<>();
        datosUsuario.put("Nombre", nombre);
        datosUsuario.put("Estaca_Distrito", estacaD);
        datosUsuario.put("Barrio_Rama", barrioR);
        datosUsuario.put("Apellido", apellido);
        datosUsuario.put("Password", rpassword);
        datosUsuario.put("Email", remail);


        myRef.child("Usuarios").push().setValue(datosUsuario);
    }

    private void registrarse() {

        String email=Email.getText().toString();
        String password=Password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registrarse.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Toast.makeText(this,"No se a podido registrar intente mas tarde",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"Registro Exitoso",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Inicio.class));
        }

    }
}

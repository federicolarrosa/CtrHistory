package com.app.ctrhistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button register, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registrarse.class);
                startActivity(intent);
            }
        });

        }

        public void LOGIN(View v)
        {
            if (validar())
            {
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, Inicio.class);
                        startActivity(intent);

                    }
                });
            }
        }

        public boolean validar()
        {

            boolean retorno = true;

            String c1 =email.getText().toString();
            String c2 = password.getText().toString();

            if (c1.isEmpty())
            {
                email.setError("Campo vacio");
                retorno = false;

            }
            if (c2.isEmpty())
            {
                password.setError("Campo vacio");
                retorno = false;
            }

            return retorno;
        }





        }









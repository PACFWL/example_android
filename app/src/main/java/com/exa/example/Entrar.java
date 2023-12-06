package com.exa.example;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Entrar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrar);
        
        Button btnEntrar = findViewById(R.id.btnCadastramentoGame);


        btnEntrar.setOnClickListener(e->{
            Intent intent = new Intent(Entrar.this, Cadastro_Game.class);
            startActivity(intent);
        });

    }
}
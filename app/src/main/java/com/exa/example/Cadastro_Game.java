package com.exa.example;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.exa.example.objects.Game;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro_Game extends AppCompatActivity {
    private static final String APP_GAME = "APP_GAME";
    FirebaseAuth mAuth;
    EditText txtName;
    EditText txtSourceSite;
    EditText txtPlatform;
    EditText txtReleaseDate;
    EditText txtDeveloper;

    Button btnSalvar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_game);

        databaseReference = FirebaseDatabase.getInstance().getReference("games");

        mAuth = FirebaseAuth.getInstance();
        txtName = findViewById(R.id.txtNameGame);
        txtSourceSite = findViewById(R.id.txtSourceSiteGame);
        txtPlatform = findViewById(R.id.txtPlatformGame);
        txtReleaseDate = findViewById(R.id.txtReleaseDateGame);
        txtDeveloper = findViewById(R.id.txtDeveloperGame);

        btnSalvar = findViewById(R.id.btnSalvarGame);

        btnSalvar.setOnClickListener(e -> salvar());

        // Add a listener to check when authentication is completed
        mAuth.addAuthStateListener(auth -> {
            if (auth.getCurrentUser() != null) {
                String token = auth.getCurrentUser().getIdToken(true).getResult().getToken();
                Log.i(APP_GAME, "TOKEN : " + token);
            } else {
                Log.i(APP_GAME, "TOKEN : null");
            }
        });
    }

    private void salvar() {
        Game game = new Game();
        game.setName(txtName.getText().toString());
        game.setSourceSite(txtSourceSite.getText().toString());
        game.setPlatform(txtPlatform.getText().toString());
        game.setReleaseDate(txtReleaseDate.getText().toString());
        game.setDeveloper(txtDeveloper.getText().toString());

        String gameId = databaseReference.push().getKey();

        if (gameId != null) {
            Intent intent = new Intent(Cadastro_Game.this, ListGames.class);
            startActivity(intent);
            game.setId(gameId);
        } else {
            Log.e(APP_GAME, "Error: gameId is null");
            return;
        }

        DatabaseReference gameRef = databaseReference.child(gameId);

        Log.i(APP_GAME, "Saving game: " + game.getName() + ", " + game.getPlatform() + ", " + game.getReleaseDate() + ", " + game.getDeveloper());

        gameRef.setValue(game)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Game cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Falha ao cadastrar game", Toast.LENGTH_SHORT).show();
                    Log.e(APP_GAME, "Erro ao cadastrar game", e);
                });
    }
}

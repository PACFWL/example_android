package com.exa.example;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exa.example.adapter.GamesAdapter;
import com.exa.example.objects.Game;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListGames extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    GamesAdapter gamesAdapter;
    ArrayList<Game> list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games);

        recyclerView = findViewById(R.id.gamesList);
        database = FirebaseDatabase.getInstance().getReference("games");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        gamesAdapter = new GamesAdapter(this, list, database);
        recyclerView.setAdapter(gamesAdapter);

        // Adicionando um divisor entre os itens da lista (opcional)
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();  // Limpa a lista antes de adicionar novos dados
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Game game = dataSnapshot.getValue(Game.class);
                    list.add(game);
                }
                gamesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}

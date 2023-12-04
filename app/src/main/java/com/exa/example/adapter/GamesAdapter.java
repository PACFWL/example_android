package com.exa.example.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legato.R;
import com.example.legato.objects.Games;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesViewHolder> {
    private Context context;
    private List<Games> list;
    private DatabaseReference databaseReference;

    public GamesAdapter(Context context, List<Games> list, DatabaseReference databaseReference) {
        this.context = context;
        this.list = list;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public GamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_games, parent, false);
        return new GamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesAdapter.GamesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Games game = list.get(position);

        holder.Name.setText(game.getName());
        holder.Platform.setText(game.getPlatform());
        holder.ReleaseDate.setText(game.getReleaseDate());
        holder.Developer.setText(game.getDeveloper());
        holder.SourceSite.setText(game.getSourceSite());

        holder.btnEditGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ... (similar code as in ArtistaAdapter)

                // Example: Replace R.layout.artist_update_popup with R.layout.game_update_popup
                final DialogPlus dialogPlus = DialogPlus.newDialog(activityContext)
                        .setContentHolder(new ViewHolder(R.layout.game_update_popup))
                        .setExpanded(true, 700)
                        .create();

                // ... (similar code as in ArtistaAdapter)

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("platform", platform.getText().toString());
                        map.put("releaseDate", releaseDate.getText().toString());
                        map.put("developer", developer.getText().toString());
                        map.put("sourceSite", sourceSite.getText().toString());

                        // Example: Replace "artists" with "games"
                        String gameKey = list.get(position).getId();

                        FirebaseDatabase.getInstance().getReference("games")
                                .child(gameKey).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.Name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.Name.getContext(), "Error While Updating.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

               
               
                // ... (similar code as in ArtistaAdapter)
            }
        });

        holder.btnDeleteGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGame(game.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void deleteGame(String gameId) {
       
       
       
       
        // ... (similar code as in ArtistaAdapter)
    }

    public static class GamesViewHolder extends RecyclerView.ViewHolder {
        TextView Name, SourceSite, Platform, ReleaseDate, Developer;
        Button btnEditGame, btnDeleteGame;

        public GamesViewHolder(@NonNull View itemView) {
            super(itemView);
            // Update the IDs according to your item_games.xml layout
            Name = itemView.findViewById(R.id.txtNameGame);
            SourceSite = itemView.findViewById(R.id.txtSourceSiteGame);
            Platform = itemView.findViewById(R.id.txtPlatformGame);
            ReleaseDate = itemView.findViewById(R.id.txtReleaseDateGame);
            Developer = itemView.findViewById(R.id.txtDeveloperGame);
            btnDeleteGame = itemView.findViewById(R.id.btnDeleteGame);
            btnEditGame = itemView.findViewById(R.id.btnEditGame);
        }
    }
}

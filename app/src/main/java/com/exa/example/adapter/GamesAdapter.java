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

import com.exa.example.R;
import com.exa.example.objects.Game;
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
    private List<Game> list;
    private DatabaseReference databaseReference;

    public GamesAdapter(Context context, List<Game> list, DatabaseReference databaseReference) {
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
        Game game = list.get(position);

        holder.Name.setText(game.getName());
        holder.Platform.setText(game.getPlatform());
        holder.ReleaseDate.setText(game.getReleaseDate());
        holder.Developer.setText(game.getDeveloper());
        holder.SourceSite.setText(game.getSourceSite());

        holder.btnEditGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

Context activityContext = null;

                if (v.getContext() instanceof Activity) {
                    activityContext = (Activity) v.getContext();
                } else if (v.getContext() instanceof ContextThemeWrapper) {
                    activityContext = ((ContextThemeWrapper) v.getContext()).getBaseContext();
                }

                if (activityContext != null) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(activityContext)
                        .setContentHolder(new ViewHolder(R.layout.game_update_popup))
                        .setExpanded(true, 700)
                        .create();
//
View view = dialogPlus.getHolderView();

                    EditText name = view.findViewById(R.id.txtGameNomeUpdate);
                    EditText platform = view.findViewById(R.id.txtGamePlataformaUpdate);
                    EditText releaseDate = view.findViewById(R.id.txtGameDataLancamentoUpdate);
                    EditText developer = view.findViewById(R.id.txtGameDeveloper);
                    EditText sourceSite = view.findViewById(R.id.txtGameSourceSite);
                    
                    Button btnUpdate = view.findViewById(R.id.btnAtualizarJogo);
                    
                    name.setText(game.getName());
                    sourceSite.setText(game.getSourceSite());
                    platform.setText(game.getPlatform());
                    developer.setText(game.getDeveloper());
                    releaseDate.setText(game.getReleaseDate());

    
                    dialogPlus.show();



                // ... 

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

               
               //
               
               } else {
                    Log.e("GamesAdapter", "Error: Unable to get the activity context");
                }
               
               
                // ...
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
       
       //
       if (gameId != null && !gameId.isEmpty()) {
            DatabaseReference gameRef = databaseReference.child(gameId);

            gameRef.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Game excluído com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Falha ao excluir game", Toast.LENGTH_SHORT).show();
                            Log.e("GamesAdapter", "Error deleting Game", e);
                        }
                    });
        } else {
            Log.e("GamesAdapter", "Error: gameId is null or empty");
        }
       

    }

    public static class GamesViewHolder extends RecyclerView.ViewHolder {
        TextView Name, SourceSite, Platform, ReleaseDate, Developer;
        Button btnEditGame, btnDeleteGame;

        public GamesViewHolder(@NonNull View itemView) {
            super(itemView);
    
Id = itemView.findViewById(R.id.txtIdGame);
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

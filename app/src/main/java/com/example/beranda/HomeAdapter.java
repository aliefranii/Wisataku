package com.example.beranda;// WisataAdapter.java
// WisataAdapter.java

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class HomeAdapter extends FirebaseRecyclerAdapter<ModelWisata, HomeAdapter.HomeViewHolder> {

    private Context context;

    public HomeAdapter(@NonNull FirebaseRecyclerOptions<ModelWisata> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeViewHolder holder, int position, @NonNull ModelWisata model) {
        Picasso.get().load(model.getGambar()).into(holder.gambarImageView);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homewisata, parent, false);
        return new HomeViewHolder(view);
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView namaWisataTextView, deskripsiTextView;
        ImageView gambarImageView;
        Button selengkapnyaButton;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            gambarImageView = itemView.findViewById(R.id.ivWisataImage);
        }
    }
}

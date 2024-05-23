package com.example.beranda;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class KabupatenAdapter extends FirebaseRecyclerAdapter<ModelKabupaten, KabupatenAdapter.KabupatenViewHolder> {

    public KabupatenAdapter(@NonNull FirebaseRecyclerOptions<ModelKabupaten> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull KabupatenViewHolder holder, int position, @NonNull ModelKabupaten model) {
        // Binding data
        holder.tvNamaKabupaten.setText(model.getNamaKabupaten());

        // Set click listener
        holder.itemView.setOnClickListener(view -> {
            Context context = view.getContext();
            String kabupatenId = getRef(position).getKey(); // Gunakan posisi yang valid
            Intent intent = new Intent(context, DaftarWisata.class);
            intent.putExtra("kabupaten_id", kabupatenId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public KabupatenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kabupaten, parent, false);
        return new KabupatenViewHolder(itemView);
    }

    public static class KabupatenViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKabupaten;

        public KabupatenViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKabupaten = itemView.findViewById(R.id.tvNamaKabupaten);
        }
    }
}
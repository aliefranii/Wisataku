package com.example.beranda;// WisataAdapter.java
// WisataAdapter.java

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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

public class WisataAdapter extends FirebaseRecyclerAdapter<ModelWisata, WisataAdapter.WisataViewHolder> {

    private Context context;

    public WisataAdapter(@NonNull FirebaseRecyclerOptions<ModelWisata> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull WisataViewHolder holder, int position, @NonNull ModelWisata model) {
        holder.namaWisataTextView.setText(model.getNamaWisata());
        holder.deskripsiTextView.setText(model.getDeskripsi());
        holder.deskripsiTextView.setMaxLines(3);
        holder.deskripsiTextView.setEllipsize(TextUtils.TruncateAt.END);
        Picasso.get().load(model.getGambar()).into(holder.gambarImageView);

        holder.selengkapnyaButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, SelengkapnyaWisata.class);
            intent.putExtra("alamat", model.getAlamat());
            intent.putExtra("deskripsi", model.getDeskripsi());
            intent.putExtra("notelepon", model.getNotelepon());
            intent.putExtra("gambar", model.getGambar());
            intent.putExtra("kabupaten_id", model.getKabupaten());
            intent.putExtra("namaWisata", model.getNamaWisata()); // Tambahkan ini
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public WisataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daftarwisata, parent, false);
        return new WisataViewHolder(view);
    }

    public static class WisataViewHolder extends RecyclerView.ViewHolder {
        TextView namaWisataTextView, deskripsiTextView;
        ImageView gambarImageView;
        Button selengkapnyaButton;

        public WisataViewHolder(@NonNull View itemView) {
            super(itemView);
            namaWisataTextView = itemView.findViewById(R.id.tvNamaWisata);
            deskripsiTextView = itemView.findViewById(R.id.tvDeskripsi);
            gambarImageView = itemView.findViewById(R.id.ivWisataImage);
            selengkapnyaButton = itemView.findViewById(R.id.btn_selengkapnya);
        }
    }
}

package com.example.beranda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DaftarWisata extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WisataAdapter wisataAdapter;
    private String kabupatenId;
    private TextView namaKabupatenTextView;
    ImageView btnInputBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarwisata);


        // Ambil kabupatenId dari Intent
        kabupatenId = getIntent().getStringExtra("kabupaten_id");

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recycleDestinasiWisata);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(this));

        // Query untuk mengambil data Wisata berdasarkan kabupaten_id
        DatabaseReference wisataRef = FirebaseDatabase.getInstance().getReference().child("Wisata");
        Query query = wisataRef.orderByChild("kabupaten_id").equalTo(kabupatenId);

        // Konfigurasi FirebaseRecyclerOptions
        FirebaseRecyclerOptions<ModelWisata> options =
                new FirebaseRecyclerOptions.Builder<ModelWisata>()
                        .setQuery(query, ModelWisata.class)
                        .build();

        // Inisialisasi adapter dan set ke RecyclerView
        wisataAdapter = new WisataAdapter(options, this);
        recyclerView.setAdapter(wisataAdapter);

        // Setup tombol kembali
        ImageView backButton = findViewById(R.id.backDaftarWst);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Menggunakan onBackPressed untuk respons cepat
            }
        });

        // Inisialisasi TextView untuk menampilkan nama kabupaten
        namaKabupatenTextView = findViewById(R.id.nama_kabupaten);

        // Ambil nama kabupaten dari Firebase
        DatabaseReference kabupatenRef = FirebaseDatabase.getInstance().getReference().child("Wisata");
        kabupatenRef.orderByChild("kabupaten_id").equalTo(kabupatenId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot kabupatenSnapshot : dataSnapshot.getChildren()) {
                        String namaKabupaten = kabupatenSnapshot.child("kabupaten_id").getValue(String.class);
                        namaKabupatenTextView.setText(namaKabupaten);
                        break;
                    }
                } else {
                    Log.d("DaftarWisata", "Data kabupaten tidak ditemukan");
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DaftarWisata", "Error fetching kabupaten: " + databaseError.getMessage());
            }



        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        wisataAdapter.startListening();

    }

    protected void onResume() {
        super.onResume();
        wisataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wisataAdapter.stopListening();
    }
}

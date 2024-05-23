package com.example.beranda;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class SelengkapnyaWisata extends AppCompatActivity {

    private TextView tvNamaWisata, tvDeskripsiWisata, tvAlamat;
    private ImageView ivWisataImage, btnInputBack;

    private Button kontak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_wisata_selengkapnya);

        // Inisialisasi view dari layout
        tvNamaWisata = findViewById(R.id.TV_namaWisata);
        tvDeskripsiWisata = findViewById(R.id.DeskripsiWisata);
        tvAlamat = findViewById(R.id.AlamatWisata);
        ivWisataImage = findViewById(R.id.ivWisataImage);
        btnInputBack = findViewById(R.id.backSelengkapnya);
        kontak = findViewById(R.id.WA);

        // Ambil data dari Intent
        String namaWisata = getIntent().getStringExtra("namaWisata");
        String notelepon = getIntent().getStringExtra("notelepon");
        String deskripsi = getIntent().getStringExtra("deskripsi");
        String alamat = getIntent().getStringExtra("alamat");
        String gambar = getIntent().getStringExtra("gambar");

        // Set data ke view
        tvNamaWisata.setText(namaWisata);
        tvDeskripsiWisata.setText(deskripsi);
        tvAlamat.setText(alamat);
        Picasso.get().load(gambar).into(ivWisataImage);

        btnInputBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Menggunakan onBackPressed untuk respons cepat
            }
        });

        kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuka aplikasi WhatsApp dan mengarahkan ke obrolan dengan nomor yang sudah diberikan
                openWhatsAppConversation(notelepon);
            }
        });
    }

    // Method untuk membuka obrolan WhatsApp dengan nomor yang diberikan
    private void openWhatsAppConversation(String phoneNumber) {
        // Memastikan nomor telepon dimulai dengan kode negara yang benar (+62) dan menghapus awalan "0" jika ada
        String phoneNumberWithCountryCode;
        if (phoneNumber.startsWith("0")) {
            // Jika nomor telepon dimulai dengan "0", menggantinya dengan kode negara +62 dan menghapus awalan "0"
            phoneNumberWithCountryCode = "+62" + phoneNumber.substring(1);
        } else {
            // Jika nomor telepon sudah memiliki awalan kode negara atau tidak memiliki awalan "0", tetapkan langsung ke phoneNumberWithCountryCode
            phoneNumberWithCountryCode = phoneNumber;
        }

        // Format nomor telepon dengan awalan "wa.me/" agar terbuka di WhatsApp
        String url = "https://wa.me/" + phoneNumberWithCountryCode;

        // Buat intent untuk membuka obrolan WhatsApp
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        // Jalankan intent
        startActivity(intent);
    }
}

package com.example.beranda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class inputwisata extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etNamaWisata, etNamaKabupaten, etAlamat, etDeskripsi, edNotelepon;
    private TextView tvSimpan;
    private ImageView ivGambar, fotoTambahkan, ikonfoto;
    private androidx.cardview.widget.CardView cvInputGambar;

    private Uri imageUri;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputwisata);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference("Wisata");

        // Inisialisasi EditText, TextView, ImageView, dan CardView
        etNamaWisata = findViewById(R.id.etNamaWisataInput);
        etNamaKabupaten = findViewById(R.id.etNamaKabupatenInput);
        etAlamat = findViewById(R.id.etalamatInput);
        etDeskripsi = findViewById(R.id.etDeskripsiInput);
        ivGambar = findViewById(R.id.ivGambar);
        fotoTambahkan = findViewById(R.id.fotoTambahkan);
        ikonfoto = findViewById(R.id.ikonfoto); // Inisialisasi ImageView ikonfoto
        cvInputGambar = findViewById(R.id.Inputgambar);
        tvSimpan = findViewById(R.id.tvSimpanInputWisata);
        edNotelepon = findViewById(R.id.etNoInput);

        ImageView backInputButton = findViewById(R.id.backinput);

        cvInputGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        tvSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        backInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat intent untuk navigasi ke HalamanMitraActivity
                Intent intent = new Intent(inputwisata.this, HalamanMitraActivity.class);
                startActivity(intent);
                // Opsional: mengakhiri aktivitas saat ini jika Anda ingin menghapusnya dari back stack
                finish();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(fotoTambahkan); // Memuat gambar ke dalam ImageView fotoTambahkan
            ikonfoto.setVisibility(View.GONE); // Menghilangkan ikon foto
        }
    }

    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + ".jpg");
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();
                                    simpanData(downloadUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(inputwisata.this, "Gagal upload gambar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            simpanData(null);
        }
    }

    private void simpanData(String imageUrl) {
        // Mendapatkan nilai dari EditText
        String namaWisata = etNamaWisata.getText().toString().trim();
        String deskripsi = etDeskripsi.getText().toString().trim();
        String kabupatenId = etNamaKabupaten.getText().toString().trim().toUpperCase(); // Mengubah ke huruf besar
        String alamat = etAlamat.getText().toString().trim();
        String notelepon = edNotelepon.getText().toString().trim();

        // Validasi input
        if (namaWisata.isEmpty() || deskripsi.isEmpty() || kabupatenId.isEmpty() || alamat.isEmpty() || notelepon.isEmpty()) {
            Toast.makeText(this, "Semua bidang harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat objek ModelInputWisata
        ModelInputWisata modelWisata = new ModelInputWisata(namaWisata, deskripsi, kabupatenId, alamat, imageUrl, notelepon);

        // Menyimpan data ke Firebase Database
        mDatabase.child("Wisata").push().setValue(modelWisata)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(inputwisata.this, "Data tersimpan", Toast.LENGTH_SHORT).show();
                        clearFields();
                        // Memulai aktivitas SplashScreenInputActivity
                        Intent intent = new Intent(inputwisata.this, SplashScreenInputActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(inputwisata.this, "Gagal menyimpan data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        etNamaWisata.setText("");
        etNamaKabupaten.setText("");
        etAlamat.setText("");
        etDeskripsi.setText("");
        edNotelepon.setText("");
        ivGambar.setImageResource(0); // Kosongkan ImageView
        fotoTambahkan.setImageResource(0); // Kosongkan ImageView
        ikonfoto.setVisibility(View.VISIBLE); // Menampilkan kembali ikon foto
    }
}



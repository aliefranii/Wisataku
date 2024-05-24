package com.example.beranda;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Profil extends Fragment {

    // Parameter arguments
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Parameters
    private String mParam1;
    private String mParam2;

    // UI elements
    private Button btnEditProfil, btnLogout, btnMitra, btnLupaPassword;
    private TextView usernameTextView;
    private ImageView imageView;

    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    public Profil() {
        // Required empty public constructor
    }

    public static Profil newInstance(String param1, String param2) {
        Profil fragment = new Profil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profil, container, false);

        // Initialize UI elements
        btnEditProfil = rootView.findViewById(R.id.btnEditProfil);
        btnLogout = rootView.findViewById(R.id.button_logout);
        btnLupaPassword = rootView.findViewById(R.id.btnlupapassword);
        btnMitra = rootView.findViewById(R.id.button3);
        usernameTextView = rootView.findViewById(R.id.username1);
        imageView = rootView.findViewById(R.id.imageView12);

        // Load user data from Firebase
        String currentUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        DatabaseReference currentUserRef = mDatabase.child("users").child(currentUserId);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent untuk memilih gambar dari galeri
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1); // Kode permintaan 1, Anda dapat memilih nomor apa saja
            }
        });


        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue(String.class);
                if (username != null) {
                    usernameTextView.setText(username);
                } else {
                    usernameTextView.setText("Username Tidak Ditemukan");
                }

                String imageUrl = dataSnapshot.child("fotoprofil").getValue(String.class);
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    // Load image using Picasso
                    Picasso.get().load(imageUrl).into(imageView);
                } else {
                    // Set a default image
                    imageView.setImageResource(R.drawable.akun);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });

        // Set OnClickListener for Edit Profile button
        btnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfilIntent = new Intent(requireContext(), EditProfil.class);
                startActivity(editProfilIntent);
            }
        });

        // Set OnClickListener for lupa password button
        btnLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfilIntent = new Intent(requireContext(), LupaPasswordActivity.class);
                startActivity(editProfilIntent);
            }
        });

        // Set OnClickListener for Logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent loginIntent = new Intent(requireContext(), halamanlogin.class);
                startActivity(loginIntent);
                requireActivity().finish(); // Close the current activity
            }
        });

        // Set OnClickListener for Mitra button
        btnMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    final String uid = currentUser.getUid();
                    mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && dataSnapshot.hasChild("mitra")) {
                                // Jika pengguna memiliki kunci mitra dalam data pengguna
                                Intent intent = new Intent(requireContext(), HalamanMitraActivity.class);
                                startActivity(intent);
                            } else {
                                // Jika pengguna tidak memiliki kunci mitra dalam data pengguna
                                Intent intent = new Intent(requireContext(), DaftarMitraActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Penanganan kesalahan jika pembatalan terjadi
                        }
                    });
                } else {
                    // Jika pengguna tidak login
                    // Mungkin tampilkan pesan atau arahkan pengguna untuk login
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                String currentUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                // Mendapatkan referensi penyimpanan Firebase
                StorageReference profileImageRef = mStorageRef.child("profile_images").child(currentUserId + ".jpg");
                // Mengunggah gambar ke Firebase Storage
                profileImageRef.putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Mendapatkan URL dari gambar yang diunggah
                                profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Menyimpan URL gambar di Firebase Realtime Database
                                        mDatabase.child("users").child(currentUserId).child("fotoprofil").setValue(uri.toString())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // Gambar profil berhasil diunggah dan URL-nya disimpan di database
                                                        Toast.makeText(requireContext(), "Gambar profil berhasil diubah", Toast.LENGTH_SHORT).show();
                                                        // Memuat gambar profil baru menggunakan Picasso
                                                        Picasso.get().load(uri).into(imageView);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Penanganan jika gagal menyimpan URL gambar di database
                                                        Toast.makeText(requireContext(), "Gagal menyimpan URL gambar di database", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Penanganan jika gagal mengunggah gambar ke Firebase Storage
                                Toast.makeText(requireContext(), "Gagal mengunggah gambar ke Firebase Storage", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Penanganan jika selectedImage null
                Toast.makeText(requireContext(), "Gagal memilih gambar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


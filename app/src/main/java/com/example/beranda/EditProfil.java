package com.example.beranda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class EditProfil extends AppCompatActivity {

    TextView ubah1, ubah2, ubah3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        ImageView backButton = findViewById(R.id.backProfil);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Menggunakan onBackPressed untuk respons cepat
            }
        });

        ubah1 = findViewById(R.id.ubahUsername);
        ubah2 = findViewById(R.id.ubahEmail);
        ubah3 = findViewById(R.id.ubahNotelepon);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference currentUserRef = usersRef.child(currentUserId);

        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String phone = dataSnapshot.child("notelepon").getValue(String.class);

                TextView usernameTextView = findViewById(R.id.username_profil1);
                TextView emailTextView = findViewById(R.id.email_profil_edit);
                TextView phoneTextView = findViewById(R.id.no_telepon);

                usernameTextView.setText(username);
                emailTextView.setText(email);
                phoneTextView.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error handling
            }
        });

        // Implementasi ubah username, email, dan nomor telepon
        implementUbahUsername();
        implementUbahEmail();
        implementUbahNotelepon();
    }

    private void implementUbahUsername() {
        ubah1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EditProfil.this);
                View view = LayoutInflater.from(EditProfil.this).inflate(R.layout.bottom_sheet_username, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String username = snapshot.child("username").getValue(String.class);
                            EditText ubahusername = view.findViewById(R.id.editUsername);
                            ubahusername.setText(username); // Mengatur teks hint dari EditText
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                Button simpan = view.findViewById(R.id.btnSimpan);
                Button batal = view.findViewById(R.id.btnBatal);

                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                simpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText ubahusername = view.findViewById(R.id.editUsername);
                        String newUsername = ubahusername.getText().toString();

                        if (newUsername.isEmpty()) {
                            ubahusername.setError("Masukkan Username");
                        } else {
                            // Mengupdate data pengguna di database waktu nyata
                            databaseReference.child("username").setValue(newUsername)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(EditProfil.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                                            bottomSheetDialog.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfil.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

    private void implementUbahEmail() {
        ubah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EditProfil.this);
                View view = LayoutInflater.from(EditProfil.this).inflate(R.layout.bottom_sheet_email, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String email = snapshot.child("email").getValue(String.class);
                            EditText ubahemail = view.findViewById(R.id.editEmail);
                            ubahemail.setText(email); // Mengatur teks hint dari EditText
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                Button simpan = view.findViewById(R.id.btnSimpan);
                Button batal = view.findViewById(R.id.btnBatal);

                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                simpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText ubahemail = view.findViewById(R.id.editEmail);
                        String newEmail = ubahemail.getText().toString();

                        if (newEmail.isEmpty()) {
                            ubahemail.setError("Masukkan Email");
                        } else {
                            // Mengupdate email pengguna di autentikasi Firebase
                            currentUser.updateEmail(newEmail)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Email di autentikasi Firebase berhasil diperbarui,
                                            // sekarang perbarui email di database Firebase Realtime atau Firestore
                                            databaseReference.child("email").setValue(newEmail)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(EditProfil.this, "Email berhasil diubah", Toast.LENGTH_SHORT).show();
                                                            bottomSheetDialog.dismiss();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(EditProfil.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfil.this, "Gagal mengubah email", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

    private void implementUbahNotelepon() {
        ubah3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EditProfil.this);
                View view = LayoutInflater.from(EditProfil.this).inflate(R.layout.bottom_sheet_notelepon, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String phone = snapshot.child("notelepon").getValue(String.class);
                            EditText ubahnotelepon = view.findViewById(R.id.editNotelepon);
                            ubahnotelepon.setText(phone); // Mengatur teks hint dari EditText
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                Button simpan = view.findViewById(R.id.btnSimpan);
                Button batal = view.findViewById(R.id.btnBatal);

                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                simpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText ubahnotelepon = view.findViewById(R.id.editNotelepon);
                        String newPhone = ubahnotelepon.getText().toString();

                        if (newPhone.isEmpty()) {
                            ubahnotelepon.setError("Masukkan Nomor Telepon");
                        } else {
                            // Mengupdate data pengguna di database waktu nyata
                            databaseReference.child("notelepon").setValue(newPhone)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(EditProfil.this, "Nomor telepon berhasil diubah", Toast.LENGTH_SHORT).show();
                                            bottomSheetDialog.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfil.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }
}

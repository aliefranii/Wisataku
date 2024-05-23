package com.example.beranda;

public class ModelWisata {
    private String kabupaten;
    private String alamat;
    private String deskripsi;
    private String gambar;
    private String namaWisata;
    private String notelepon;

    // Default constructor required for calls to DataSnapshot.getValue(ModelWisata.class)
    public ModelWisata() {
    }

    public ModelWisata(String kabupaten, String alamat, String deskripsi, String gambar, String namaWisata, String notelepon) {
        this.kabupaten = kabupaten;
        this.alamat = alamat;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.namaWisata = namaWisata;
        this.notelepon = notelepon;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNamaWisata() {
        return namaWisata;
    }

    public void setNamaWisata(String namaWisata) {
        this.namaWisata = namaWisata;
    }

    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }
}

package com.example.beranda;

public class ModelInputWisata {

    private String namaWisata;
    private String deskripsi;
    private String kabupaten_id;
    private String alamat;
    private String gambar;
    private String notelepon; // Menambahkan field untuk nomor telepon

    public ModelInputWisata(String namaWisata, String deskripsi, String kabupaten_id, String alamat, String gambar, String notelepon) {
        this.namaWisata = namaWisata;
        this.deskripsi = deskripsi;
        this.kabupaten_id = kabupaten_id;
        this.alamat = alamat;
        this.gambar = gambar;
        this.notelepon = notelepon; // Menginisialisasi nilai notelepon
    }

    public ModelInputWisata() {
        // Default constructor diperlukan untuk panggilan toDataSnapshot()
    }

    public String getNamaWisata() {
        return namaWisata;
    }

    public void setNamaWisata(String namaWisata) {
        this.namaWisata = namaWisata;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKabupaten_id() {
        return kabupaten_id;
    }

    public void setKabupaten_id(String kabupaten_id) {
        this.kabupaten_id = kabupaten_id;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }
}

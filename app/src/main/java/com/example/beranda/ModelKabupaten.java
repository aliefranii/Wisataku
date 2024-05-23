package com.example.beranda;

public class ModelKabupaten {
    String namaKabupaten, kabupaten_id;

    ModelKabupaten()
    {

    }

    public ModelKabupaten(String namaKabupaten, String kabupaten_id) {
        this.namaKabupaten = namaKabupaten;
        this.kabupaten_id = kabupaten_id;
    }

    public String getNamaKabupaten() {
        return namaKabupaten;
    }

    public void setNamaKabupaten(String namaKabupaten) {
        this.namaKabupaten = namaKabupaten;
    }

    public String getKabupaten_id() {
        return kabupaten_id;
    }

    public void setKabupaten_id(String kabupaten_id) {
        this.kabupaten_id = kabupaten_id;
    }

    public boolean getId() {
        return false;
    }
}

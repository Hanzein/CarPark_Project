package com.example.projectpark;

public class MainModel {

    String merk, nama, pemilik, plat, turl;

    MainModel(){

    }

    public MainModel(String merk, String nama, String pemilik, String plat, String turl) {
        this.merk = merk;
        this.nama = nama;
        this.pemilik = pemilik;
        this.plat = plat;
        this.turl = turl;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}

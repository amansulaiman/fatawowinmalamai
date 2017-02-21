package com.bigscreen.fatawowinmalamai.Entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Abdulrahman  on 2/16/17.
 */

public class Fatawa implements Serializable{
    private boolean anYijawabi;
    private String jawabinMalami;
    private String sunanTambaya;
    private String jawabinTambaya;
    private String maiTambaya;
    private Date lokacinTambaya;
    private String sunanMalami;

    public Fatawa(boolean anYijawabi, String jawabinMalami, String sunanTambaya, String jawabinTambaya, String maiTambaya, Date lokacinTambaya, String sunanMalami) {
        this.anYijawabi = anYijawabi;
        this.jawabinMalami = jawabinMalami;
        this.sunanTambaya = sunanTambaya;
        this.jawabinTambaya = jawabinTambaya;
        this.maiTambaya = maiTambaya;
        this.lokacinTambaya = lokacinTambaya;
        this.sunanMalami = sunanMalami;
    }

    public Fatawa() {
    }

    public boolean isAnYijawabi() {
        return anYijawabi;
    }

    public void setAnYijawabi(boolean anYijawabi) {
        this.anYijawabi = anYijawabi;
    }

    public String getSunanTambaya() {
        return sunanTambaya;
    }

    public void setSunanTambaya(String sunanTambaya) {
        this.sunanTambaya = sunanTambaya;
    }

    public String getJawabinMalami() {
        return jawabinMalami;
    }

    public void setJawabinMalami(String jawabinMalami) {
        this.jawabinMalami = jawabinMalami;
    }

    public String getJawabinTambaya() {
        return jawabinTambaya;
    }

    public void setJawabinTambaya(String jawabinTambaya) {
        this.jawabinTambaya = jawabinTambaya;
    }

    public String getMaiTambaya() {
        return maiTambaya;
    }

    public void setMaiTambaya(String maiTambaya) {
        this.maiTambaya = maiTambaya;
    }

    public Date getLokacinTambaya() {
        return lokacinTambaya;
    }

    public void setLokacinTambaya(Date lokacinTambaya) {
        this.lokacinTambaya = lokacinTambaya;
    }

    public String getSunanMalami() {
        return sunanMalami;
    }

    public void setSunanMalami(String sunanMalami) {
        this.sunanMalami = sunanMalami;
    }

    @Override
    public String toString() {
        return "Fatawa (" + sunanTambaya + ")" + "{" +
                "Mai Tambaya =" + maiTambaya +
                ", Tambaya='" + jawabinTambaya + '\'' +
                ", Anyi Jawabi ='" + anYijawabi + '\'' +
                ", Jwawabin Malami='" + jawabinMalami + '\'' +
                ", Lokacin Tambaya='" + lokacinTambaya.toString() + '\'' +
                '}';
    }
}

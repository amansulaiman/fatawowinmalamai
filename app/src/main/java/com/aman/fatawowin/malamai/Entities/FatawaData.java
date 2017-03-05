package com.aman.fatawowin.malamai.Entities;

import java.util.Date;

/**
 * Created by aman on 3/1/17.
 */

public class FatawaData extends Fatawa{
    public String key;

    public FatawaData(boolean anYijawabi, String jawabinMalami, String sunanTambaya, String jawabinTambaya, String maiTambaya, Date lokacinTambaya, String sunanMalami, String malamiID, String key) {
        super(anYijawabi, jawabinMalami, sunanTambaya, jawabinTambaya, maiTambaya, lokacinTambaya, sunanMalami, malamiID);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

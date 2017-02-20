package com.bigscreen.fatawowinmalamai.DummyData;

import com.bigscreen.fatawowinmalamai.Entities.Fatawa;
import com.bigscreen.fatawowinmalamai.Entities.Status;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 2/16/17.
 */

public class DataServer {

    private static final String HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK = "https://avatars1.githubusercontent.com/u/7698209?v=3&s=460";
    private static final String CYM_CHAD = "CymChad";

    private DataServer() {
    }

    public static List<Fatawa> getAllFatwa() {


        List<Fatawa> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Fatawa fatawa = new Fatawa();
            //TODO: add fatawa
            list.add(fatawa);
        }
        return list;
    }
}

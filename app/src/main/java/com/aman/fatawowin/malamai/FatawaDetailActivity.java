package com.aman.fatawowin.malamai;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aman.fatawowin.malamai.Entities.FatawaData;
import com.aman.fatawowin.malamai.R;

import java.text.SimpleDateFormat;

public class FatawaDetailActivity extends AppCompatActivity {

    private TextView jawabinMalami;
    private TextView sunanTambaya;
    private  TextView jawabinTambaya;
    private TextView sunanMalami;
    private TextView lokacinTambaya;
    private FatawaData currentFatawa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatawa_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        currentFatawa = (FatawaData) getIntent().getExtras().getSerializable("FatawaData");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String date = format.format(currentFatawa.getLokacinTambaya());
        jawabinMalami = (TextView) findViewById(R.id.jawabinMalami);
        jawabinTambaya = (TextView) findViewById(R.id.jawabinTambaya);
        sunanTambaya = (TextView) findViewById(R.id.sunanTambaya);
        lokacinTambaya = (TextView) findViewById(R.id.lokacinTambaya);
        sunanMalami = (TextView) findViewById(R.id.sunanMalami);

        jawabinMalami.setText(currentFatawa.getJawabinMalami());
        jawabinTambaya.setText(currentFatawa.getJawabinTambaya());
        sunanTambaya.setText(currentFatawa.getSunanTambaya());
        lokacinTambaya.setText(date);
        sunanMalami.setText(currentFatawa.getSunanMalami());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.pranav.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainJavaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    public static LinearLayout resultlayout;
    public static Button retest;

    public static TextView dspeed,uspeed,results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        dspeed = findViewById(R.id.dspeed);
        uspeed =findViewById(R.id.uspeed);
        results = findViewById(R.id.result);

        results.setVisibility(View.GONE);
        resultlayout = findViewById(R.id.resultview);
        resultlayout.setVisibility(View.GONE);

        retest = findViewById(R.id.retest);
        retest.setVisibility(View.GONE);

        adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        InternetSpeedBuilder builder = new InternetSpeedBuilder(this);
        builder.setOnEventInternetSpeedListener(new InternetSpeedBuilder.OnEventInternetSpeedListener() {
            @Override
            public void onDownloadProgress(int count, ProgressionModel progressModel) {

            }

            @Override
            public void onUploadProgress(int count, ProgressionModel progressModel) {

            }

            @Override
            public void onTotalProgress(int count, ProgressionModel progressModel) {
                adapter.setDataList(count, progressModel);

            }
        });
        builder.start("http://www.speedtest.net/mini.php", 20);

        retest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),MainJavaActivity.class));
            }
        });

    }

}

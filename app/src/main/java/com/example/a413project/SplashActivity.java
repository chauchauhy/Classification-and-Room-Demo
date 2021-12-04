package com.example.a413project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a413project.Database.model.DataList;

import java.io.Serializable;

public class SplashActivity extends AppCompatActivity {
    TextView loading;
    ProgressBar progressBar;
    ConstraintLayout layout;
    DataList datalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.contentHolder);
        loadDataList();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

    }
    // init the static record list from Room database
    private void loadDataList(){
        datalist = new DataList(this);
    }



}
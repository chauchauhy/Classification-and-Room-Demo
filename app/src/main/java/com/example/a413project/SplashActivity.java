package com.example.a413project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    TextView loading;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = findViewById(R.id.loadingTv_splash);
        progressBar = findViewById(R.id.progressBar_splash);
        loadingDatabase();

        startActivity( new Intent(SplashActivity.this, HomeActivity.class));

    }
    public void loadingDatabase(){

    }

}
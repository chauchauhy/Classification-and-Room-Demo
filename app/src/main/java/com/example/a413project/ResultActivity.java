package com.example.a413project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a413project.Database.model.Classification;
import com.example.a413project.Database.model.DataList;

import java.io.IOException;
import java.util.Locale;


public class ResultActivity extends AppCompatActivity {
    Classification c;
    public static final String CLASSIFICATION_CODE = "Classification";
    public static final String SHOW = "SHOWRESULT";
    public static final String EDITED = "EDITED";
    ImageView imageView;
    TextView timetemp, confidence;
    EditText label;
    ImageButton submit, listen;
    DataList dataList;
    Context context;
    boolean flag;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initUI();

    }
    private void initUI(){
        try{
            c = (Classification) getIntent().getSerializableExtra(CLASSIFICATION_CODE);
            flag = getIntent().getBooleanExtra(SHOW, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        context = this;
        dataList = new DataList(this);
        imageView = findViewById(R.id.resultImage);
        timetemp = findViewById(R.id.timeTempResult);
        label = findViewById(R.id.labelResult);
        confidence = findViewById(R.id.confidenceResult);
        submit = findViewById(R.id.submitBtnResult);
        listen = findViewById(R.id.listenBtnResult);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if(c!=null){
            try {
                Uri uri = Uri.parse(c.getFilePath());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                if(bitmap!=null) {
                    imageView.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                imageView.setImageDrawable(getDrawable(R.drawable.ic_baseline_error_24));
                Toast.makeText(context, "The image not found", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            timetemp.setText(c.timeConverter());
            label.setText( c.getLabel() );
            if(c.getEdit().equals(EDITED)){
                confidence.setText(R.string.changed);
            }else{
                confidence.setText(getString(R.string.confidenceResultText) + " " + c.getConfidenceWithString());
            }
        }
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.UK);
                }else{
                    listen.setEnabled(false);
                }
            }
        });
    }

    public void onSubmit(View view) {
        if (view.getId() == R.id.submitBtnResult) {
            if (!label.getText().toString().trim().toLowerCase().equals(c.getLabel().trim().toLowerCase())) {
                c.setLabel(label.getText().toString().trim());
                c.setEdit("EDITED");
            }
            if (flag) {
                dataList.addItem(c);
            } else {
                c.setEdit("EDITED");
                dataList.update(c);
            }
            startActivity(new Intent(context, HomeActivity.class));
            this.finish();
        }

    }

    public void onListen(View view) {
        if(view.getId() == R.id.listenBtnResult){
            try{
                if (label.getText().toString().trim().length()>0){
                    textToSpeech.speak(label.getText().toString().trim(), TextToSpeech.QUEUE_FLUSH, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            startActivity(new Intent(context, HomeActivity.class));

        return super.onOptionsItemSelected(item);
    }
}
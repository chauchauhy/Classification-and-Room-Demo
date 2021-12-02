package com.example.a413project.Database.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity(tableName = "ClassificationTable")
public class Classification implements Serializable {
    // auto increase the id
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String label;
    private float confidence;
    private String views;
    private String filePath;
    private String timeStemp;

    public void setTimeStemp(String timeStemp) {
        this.timeStemp = timeStemp;
    }

    public String getTimeStemp() {
        return timeStemp;
    }

    public void setTimeTemp(String timeStemp) {
        this.timeStemp = timeStemp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getConfidence() {
        return confidence;
    }

    public String getConfidenceWithString() {
        return String.format("%.02f", confidence*100) + "%";
    }

    public void setConfidence(float label) {
        this.confidence = confidence;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Classification() {
    }

    public Classification(String label, float confidence, String views, String filePath) {
        this.label = label;
        this.confidence = confidence;
        this.views = views;
        this.filePath = filePath;
        this.timeStemp = String.valueOf(System.currentTimeMillis());
    }

    @Ignore
    public Classification(int id, String label, float confidence, String views, String filePath) {
        this.id = id;
        this.label = label;
        this.confidence = confidence;
        this.views = views;
        this.filePath = filePath;
        this.timeStemp = String.valueOf(System.currentTimeMillis());
    }
    public String timeConverter(){
        Date df = new java.util.Date(Long.parseLong(timeStemp));
        return new SimpleDateFormat("MM/dd/yyyy, hh:mma").format(df);
    }

    @Override
    public String toString() {
        return "Classification{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", confidence=" + confidence +
                ", views='" + views + '\'' +
                ", filePath='" + filePath + '\'' +
                ", timeStemp='" + timeConverter() + '\'' +
                '}';
    }
}


package com.example.a413project.Database.model;


import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Classification {
    // auto increase the id
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Label[] labels;
    private String views;
    private String filePath;
    private String timeStemp;

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

    public Label[] getLabels() {
        return labels;
    }

    public void setLabels(Label[] labels) {
        this.labels = labels;
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

    public Classification(Label[] labels, String views, String filePath, String timeStemp) {
        this.labels = labels;
        this.views = views;
        this.filePath = filePath;
        this.timeStemp = timeStemp;
    }

    @Ignore
    public Classification(int id, Label[] labels, String views, String filePath) {
        this.id = id;
        this.labels = labels;
        this.views = views;
        this.filePath = filePath;
        this.timeStemp = String.valueOf(System.currentTimeMillis());
    }
    public String timeConverter(){
        Date df = new java.util.Date(Long.parseLong(timeStemp));
        return new SimpleDateFormat("MM dd yyyy hh:mma").format(df);
    }
}


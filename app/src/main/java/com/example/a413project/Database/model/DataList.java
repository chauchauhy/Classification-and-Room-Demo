package com.example.a413project.Database.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.a413project.Database.DataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataList {
    public static ArrayList<Classification> list = new ArrayList<>();
    Context context;

    public DataList(Context context) {
        this.context = context;
        refreshList();
    }

    public void addItem(Classification c) {
        addItemIntoDatabase(c);
    }

    public void update(Classification c){
        upDateItem(c);
    }

    public void remove(Classification c){
        try {
            deleteItemDatabase(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(c.getFilePath());
        boolean result = file.delete();
    }

    public void removeAll(){
        new Thread(()->{
            DataBase.getInstance(context).getDAO().removeAllData();
        }).start();
        refreshList();
    }

    private void addItemIntoDatabase(Classification c){
        new Thread(() -> {
            DataBase.getInstance(context).getDAO().insertData(c);
        }).start();
        refreshList();
    }

    private void upDateItem(Classification c){
        new Thread(()->{
            DataBase.getInstance(context).getDAO().updateData(c);
        }).start();
        refreshList();
    }

    private void deleteItemDatabase(Classification c){
        new Thread(()->{
            DataBase.getInstance(context).getDAO().deleteData(c);
        }).start();
        refreshList();
    }

    public void refreshList(){
        list.clear();
        new Thread(() -> {
            List<Classification> d = DataBase.getInstance(context).getDAO().selectAll();
            for (Classification c : d) {
                list.add(c);
            }
        }).start();
    }

    public ArrayList<Classification> getList() {
        return list;
    }

    public Uri saveToGallery(Bitmap bitmap){
        FileOutputStream fileOutputStream = null;
        //get folder path
        File file = Environment.getExternalStorageDirectory();
        // create the image folder if not existing
        File dir = new File(file.getAbsolutePath()+ "/pictures/Classification/pictures");
        dir.mkdirs();
        String filename = String.format("%d.png", System.currentTimeMillis());
        File outFile = new File(dir, filename);
        Log.i("ACTIVI", outFile.toString());
        try{
            fileOutputStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            Log.i("ACTIVI", e.toString());

            e.printStackTrace();
        }
        // compress to png file with 100% quality
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        try{
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            Log.i("ACTIVI", e.toString());
            e.printStackTrace();
        }
        // transfer the file to Uri (android ) not URL(JAVA)
        Uri uri = Uri.fromFile(outFile);
        return uri;


    }


}

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

// the model of the data
public class DataList {
    public static ArrayList<Classification> list = new ArrayList<>();
    Context context;

    public DataList(Context context) {
        this.context = context;
        refreshList();
    }
    // add the item to the room dn
    public void addItem(Classification c) {
        addItemIntoDatabase(c);
    }
    // update the item (replace)
    public void update(Classification c){
        upDateItem(c);
    }
    // remove the item and try to remove the image record of the record
    // for the future dev. it may need content provider to update/delete/insert the image
    public void remove(Classification c){
        try {
            deleteItemDatabase(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // convert String to uri
            Uri uri = Uri.parse(c.getFilePath());
            // getPath return uri path in mobile
            File file = new File(uri.getPath());
            if (file.exists()) {
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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
    // catch the new record from db
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

    // save to device
    public Uri saveToGallery(Bitmap bitmap){
        FileOutputStream fileOutputStream = null;
        //get folder path
        File file = Environment.getExternalStorageDirectory();
        // create the image folder if not existing
        File dir = new File(file.getAbsolutePath()+ "/pictures/Classification/pictures");
        // make directory if dir not exist
        dir.mkdirs();
        String filename = String.format("%d.png", System.currentTimeMillis());
        File outFile = new File(dir, filename);
        try{
            fileOutputStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // compress to png file with 100% quality
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        try{
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // transfer the file to Uri (android) not URL(JAVA)
        Uri uri = Uri.fromFile(outFile);
        return uri;


    }


}

package com.example.a413project.Database.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.a413project.Database.DataBase;

import java.io.File;
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
        refreshList();
    }

    public void update(Classification c){
        upDateItem(c);
        refreshList();
    }

    public void remove(Classification c){
        try {
            deleteItemDatabase(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(c.getFilePath());
        boolean result = file.delete();
        refreshList();
    }

    public void removeAll(){
        new Thread(()->{
            DataBase.getInstance(context).getDAO().removeAllData();
        }).start();
    }

    private void addItemIntoDatabase(Classification c){
        new Thread(() -> {
            DataBase.getInstance(context).getDAO().insertData(c);
        }).start();
    }

    private void upDateItem(Classification c){
        new Thread(()->{
            DataBase.getInstance(context).getDAO().updateData(c);
        }).start();
    }

    private void deleteItemDatabase(Classification c){
        new Thread(()->{
            DataBase.getInstance(context).getDAO().deleteData(c);
        }).start();
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

}

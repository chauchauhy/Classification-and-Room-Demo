package com.example.a413project.Database.model;

import android.content.Context;
import android.util.Log;

import com.example.a413project.Database.DataBase;

import java.util.ArrayList;
import java.util.List;

public class DataList {
    private static ArrayList<Classification> list = new ArrayList<>();
    Context context;

    public DataList(Context context) {
        this.context = context;
    }

    public void init(){
        Classification c = new Classification("Title", 99, "views", "path");
        for (int i =0; i<10; i++){
            addItem(c);
        }
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
        deleteItemDatabase(c);
        refreshList();
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
                Log.i("ACITI", c.toString());
            }
        }).start();
    }

    public ArrayList<Classification> getList() {
        return list;
    }



}

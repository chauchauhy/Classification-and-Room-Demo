package com.example.a413project.Database.model;

import java.util.ArrayList;

public class DataList {
    private ArrayList<Classification> classifications;

    public DataList(){};

    public void addItem(Classification c){
        this.classifications.add(c);
    }
    public ArrayList<Classification> getList(){
        return classifications;
    }

    public void removeItem(int id){
        classifications.remove(id);
    }


}

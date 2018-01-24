package com.example.mac.mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by mac on 2018. 1. 24..
 */

public class Item3 {

    private String name;

    public Item3(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.example.mac.mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.mac.mvvm.BR;

/**
 * Created by mac on 2018. 1. 24..
 */

public class Item extends BaseObservable{

    private String name;

    public Item(String name) {
        this.name = name;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}

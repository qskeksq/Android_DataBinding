package com.example.mac.mvvm.bindingConversion;

import android.databinding.BindingConversion;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 2018. 1. 24..
 */

public class MainConversion {


    @BindingConversion
    public static String convertDateToDisplayedText(Date date) {
        return new SimpleDateFormat("yyyy.MM.dd").format(date);
    }

    @BindingConversion
    public static int convertBooleanToVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }


}

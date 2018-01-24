package com.example.mac.mvvm.bindingAdapter;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.mvvm.DumAdapter;
import com.example.mac.mvvm.model.Item;

/**
 * Created by mac on 2018. 1. 24..
 */

public class MainBinding {

    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<Item> item) {
        DumAdapter adapter =(DumAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(item);
        }
    }

    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + fontName));
    }

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable) {
        // ImageUtil.loadImage(imageView, url, errorDrawable);
    }



}

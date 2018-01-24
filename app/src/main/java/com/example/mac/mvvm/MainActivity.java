package com.example.mac.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mac.mvvm.databinding.ActivityMainBinding;
import com.example.mac.mvvm.model.Item;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ObservableArrayList<Item> list;
    DumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBinding();
        bindRecycler();
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVariable(BR.content, this);
        binding.setVariable(BR.item, new Item("name"));
    }

    public void onButtonClick(View view){
        Toast.makeText(this,"Button Click",Toast.LENGTH_SHORT).show();
    }


    private void bindRecycler() {
        // 어댑터
        adapter = new DumAdapter();
        // 어댑터 세팅
        binding.recyclerView.setAdapter(adapter);

        // 데이터
        list = new ObservableArrayList<>();
        list.add(new Item("one"));
        list.add(new Item("two"));
        list.add(new Item("three"));
        // 데이터 세팅
        binding.setItemList(list);
    }


    public List<Item> getData() {
        List<Item> list = new ArrayList<>();
        list.add(new Item("one"));
        list.add(new Item("two"));
        list.add(new Item("three"));
        return list;
    }

}

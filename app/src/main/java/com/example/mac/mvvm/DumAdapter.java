package com.example.mac.mvvm;

import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mac.mvvm.databinding.ItemBinding;
import com.example.mac.mvvm.model.Item;

/**
 * Created by mac on 2018. 1. 24..
 */
public class DumAdapter extends RecyclerView.Adapter<DumAdapter.Holder> {

    ObservableArrayList<Item> list;

    public void setItem(ObservableArrayList<Item> list){
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBinding binding = ItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.binding.setItem(list.get(position));
        // or holder.binding.setVariable(BR.item, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        ItemBinding binding;

        public Holder(ItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

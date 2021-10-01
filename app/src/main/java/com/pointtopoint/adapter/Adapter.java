package com.pointtopoint.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract  class Adapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

  Context context;
  ArrayList<T> list;
  public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

  public abstract void onBindData(RecyclerView.ViewHolder holder, T val , int position);


  public Adapter(Context context, ArrayList<T> list) {
    this.context = context;
    this.list = list;
  }


  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

    RecyclerView.ViewHolder holder = setViewHolder(viewGroup);

    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    onBindData(viewHolder, list.get(i), i);

  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public void addItems( ArrayList<T> savedCardItemz){
    list = savedCardItemz;
    this.notifyDataSetChanged();
  }

  public T getItem(int position){
    return list.get(position);
  }

  public void filterList(ArrayList<T> filteredList) {
    list = filteredList;
    notifyDataSetChanged();
  }
}

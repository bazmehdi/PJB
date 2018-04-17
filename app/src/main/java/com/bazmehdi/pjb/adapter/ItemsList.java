package com.bazmehdi.pjb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bazmehdi.pjb.R;
import com.bazmehdi.pjb.model.ItemModel;

import java.util.List;

public class ItemsList extends RecyclerView.Adapter<ItemsList.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    List<ItemModel> itemList;

    public ItemsList(Context context, List<ItemModel> itemList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.itemList=itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewName.setText(String.valueOf(itemList.get(position)));
        holder.textViewCategory.setText(String.valueOf(itemList.get(position)));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName;
    TextView textViewCategory;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
        }
    }
}
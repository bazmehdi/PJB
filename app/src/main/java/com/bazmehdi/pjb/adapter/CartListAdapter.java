package com.bazmehdi.pjb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bazmehdi.pjb.R;
import com.bazmehdi.pjb.model.ItemModel;

import com.balysv.materialripple.MaterialRippleLayout; // Ripple effect wrapper for Android views at: https://github.com/balysv/material-ripple
import com.squareup.picasso.Picasso; // Image-loading and caching library at: https://github.com/square/picasso

import java.util.ArrayList;
import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> implements Filterable {

    private final int mBackground;
    private List<ItemModel> original_items;
    private List<ItemModel> filtered_items;
    private ItemFilter mFilter = new ItemFilter();


    private final TypedValue mTypedValue = new TypedValue();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ItemModel obj);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Each data item is just a string in this case
        public TextView title;
        public TextView category;
        public TextView price;
        public TextView total;
        public ImageView image;
        public MaterialRippleLayout lyt_parent;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            category = v.findViewById(R.id.category);
            price = v.findViewById(R.id.price);
            total = v.findViewById(R.id.total);
            image = v.findViewById(R.id.image);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

    // Returns a filter, in this case being mFilter of type ItemFilter
    public Filter getFilter() {
        return mFilter;
    }

    // Constructor
    public CartListAdapter(Context ctx, List<ItemModel> items) {
        this.ctx = ctx;
        original_items = items;
        filtered_items = items;
        ctx.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
    }

    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        v.setBackgroundResource(mBackground);
        // Set the views' size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ItemModel p = filtered_items.get(position);
        holder.title.setText(p.getName());
        holder.category.setText(p.getCategory());
        holder.total.setText(p.getTotal() + " X");
        holder.price.setText(p.getStrPrice());
        Picasso.with(ctx).load(p.getImg())
                .resize(100, 100)
                .into(holder.image);
        // View detail message conversation
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position, p);
                }
            }
        });
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return filtered_items.size();
    }

    // Return the position of each item in the dataset
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Performs the actual filtering
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<ItemModel> list = original_items;
            final List<ItemModel> result_list = new ArrayList<>(list.size());

            for (int i = 0; i < list.size(); i++) {
                String str_title = list.get(i).getName();
                String str_cat = list.get(i).getCategory();
                if (str_title.toLowerCase().contains(query) || str_cat.toLowerCase().contains(query)) {
                    result_list.add(list.get(i));
                }
            }

            results.values = result_list;
            results.count = result_list.size();

            return results;
        }

        // Publishes the filtered results to the UI
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtered_items = (List<ItemModel>) results.values;
            notifyDataSetChanged();
        }
    }
}

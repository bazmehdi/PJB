package com.bazmehdi.pjb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bazmehdi.pjb.R;
import com.bazmehdi.pjb.model.ItemModel;

import com.squareup.picasso.Picasso; // Image-loading and caching library at: https://github.com/square/picasso

import java.util.ArrayList;
import java.util.List;

public class CategoryItemGridAdapter extends RecyclerView.Adapter<CategoryItemGridAdapter.ViewHolder> implements Filterable {

    private final int mBackground;
    private List<ItemModel> original_items;
    private List<ItemModel> filtered_items;
    private ItemFilter mFilter = new ItemFilter();

    private final TypedValue mTypedValue = new TypedValue();

    private Context ctx;
    private boolean clicked = false;

    // For item click listener
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(View view, ItemModel obj, int position);
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Each data item is just a string in this case
        public TextView title;
        public TextView category;
        public TextView price;
        public ImageView image;
        public RelativeLayout lyt_parent;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            category = v.findViewById(R.id.category);
            price = v.findViewById(R.id.price);
            image = v.findViewById(R.id.image);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

    // Returns a filter, in this case being mFilter of type ItemFilter
    public Filter getFilter() {
        return mFilter;
    }

    // Constructor
    public CategoryItemGridAdapter(Context ctx, List<ItemModel> items) {
        this.ctx = ctx;
        original_items = items;
        filtered_items = items;
        ctx.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
    }

    @Override
    public CategoryItemGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        v.setBackgroundResource(mBackground);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ItemModel p = filtered_items.get(position);
        holder.title.setText(p.getName());
        holder.category.setText(p.getCategory());
        holder.price.setText(p.getStrPrice());
        Picasso.with(ctx).load(p.getImg()).into(holder.image);

        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clicked && mOnItemClickListener != null){
                    clicked = true;
                    mOnItemClickListener.onItemClick(view, p, position);
                }
            }
        });
        clicked = false;
    }


    // Here is the key method to apply the animation
    private int lastPosition = -1;
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.slide_in_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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
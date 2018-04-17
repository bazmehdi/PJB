package com.bazmehdi.pjb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bazmehdi.pjb.ItemDetails;
import com.bazmehdi.pjb.MainActivity;
import com.bazmehdi.pjb.R;
import com.bazmehdi.pjb.adapter.ItemGridAdapter;
import com.bazmehdi.pjb.data.Constant;
import com.bazmehdi.pjb.data.Tools;
import com.bazmehdi.pjb.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    public static String TAG_CATEGORY = "com.bazmehdi.pjb.tagCategory";

    private View view;
    private RecyclerView recyclerView;
    private ItemGridAdapter mAdapter;
    private LinearLayout lyt_notfound;
    private String category = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, null);
        category = getArguments().getString(TAG_CATEGORY);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        lyt_notfound = (LinearLayout) view.findViewById(R.id.lyt_notfound);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), Tools.getGridSpanCount(getActivity()));
        recyclerView.setLayoutManager(mLayoutManager);

        //set data and list adapter
        List<ItemModel> items = new ArrayList<>();
        //if(category.equals(getString(R.string.menu_cat1))){
        //    items = Constant.getItemClothes(getActivity());
        //}else if(category.equals(getString(R.string.menu_cat2))){
        //    items = Constant.getItemShoes(getActivity());
        //}else if(category.equals(getString(R.string.menu_cat3))){
        //    items = Constant.getItemWatches(getActivity());
        //}else if(category.equals(getString(R.string.menu_cat4))){
        //    items = Constant.getItemAccessories(getActivity());
        //}else if(category.equals(getString(R.string.menu_cat5))){
        //    items = Constant.getItemBags(getActivity());
        //}
        mAdapter = new ItemGridAdapter(getActivity(), items);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ItemGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, ItemModel obj, int position) {
                ItemDetails.navigate((MainActivity)getActivity(), v.findViewById(R.id.lyt_parent), obj);
            }
        });

        if(mAdapter.getItemCount()==0){
            lyt_notfound.setVisibility(View.VISIBLE);
        }else{
            lyt_notfound.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onResume() {
        mAdapter.notifyDataSetChanged();
        super.onResume();
    }
}

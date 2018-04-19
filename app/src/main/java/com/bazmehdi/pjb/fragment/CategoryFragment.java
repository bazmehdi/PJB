package com.bazmehdi.pjb.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bazmehdi.pjb.ItemDetails;
import com.bazmehdi.pjb.MainActivity;
import com.bazmehdi.pjb.R;
import com.bazmehdi.pjb.adapter.ItemGridAdapter;
import com.bazmehdi.pjb.data.Tools;
import com.bazmehdi.pjb.model.ItemModel;
import com.bazmehdi.pjb.FoodListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CategoryFragment extends Fragment {

    public static String TAG_CATEGORY = "com.bazmehdi.pjb.tagCategory";

    private View view;
    private RecyclerView recyclerView;
    private ItemGridAdapter mAdapter;
    private LinearLayout lyt_notfound;
    private String category = "";

    private DatabaseReference db;

    public ArrayList<String> biscuitsNameArray = new ArrayList<>();
    public ArrayList<String> biscuitsPrcArray = new ArrayList<>();
    public ArrayList<String> biscuitsImgArray = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseDatabase.getInstance().getReference();

        //FoodListener fl = new FoodListener(this);
        //db.addChildEventListener(fl);

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot childSnapshot : dataSnapshot.child("Biscuits").getChildren()) {
                    biscuitsNameArray.add(childSnapshot.child("itemName").getValue().toString());
                    biscuitsPrcArray.add(childSnapshot.child("itemPrc").getValue().toString());
                    biscuitsImgArray.add(childSnapshot.child("itemImg").getValue().toString());
                }

                Log.d("names list", biscuitsNameArray.toString());
                Log.d("price list", biscuitsPrcArray.toString());
                Log.d("image list", biscuitsImgArray.toString());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, null);
        category = getArguments().getString(TAG_CATEGORY);
        recyclerView = view.findViewById(R.id.recyclerView);
        lyt_notfound = view.findViewById(R.id.lyt_notfound);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), Tools.getGridSpanCount(getActivity()));
        recyclerView.setLayoutManager(mLayoutManager);

        //set data and list adapter
        List<ItemModel> items = new ArrayList<>();
        if(category.equals(getString(R.string.menu_cat1))){
            items = getSavoury(getActivity());
        }else if(category.equals(getString(R.string.menu_cat2))){
            items = getPastries(getActivity());
        }else if(category.equals(getString(R.string.menu_cat3))){
            items = getCakes(getActivity());
        }else if(category.equals(getString(R.string.menu_cat4))){
            items = getBiscuits(getActivity());
        }
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

    private static Random rnd = new Random();

    public List<ItemModel> getSavoury(Context ctx) {
        List<ItemModel> items = new ArrayList<>();
        TypedArray img_s = ctx.getResources().obtainTypedArray((R.array.img_savoury));
        String[] name_s = ctx.getResources().getStringArray(R.array.str_savoury);
        String[] prc_s = ctx.getResources().getStringArray(R.array.prc_savoury);
        List<Integer> img_s_list = convertToInt(img_s);
        List<String> name_s_list = Arrays.asList(name_s);
        List<String> prc_s_list = Arrays.asList(prc_s);
        for (int i = 0; i <img_s_list.size(); i++) {
            ItemModel item = new ItemModel(Long.parseLong("1" + i), img_s_list.get(i), name_s_list.get(i), Long.parseLong(prc_s_list.get(i)), ctx.getString(R.string.menu_cat1));
            items.add(item);
        }
        Collections.shuffle(items, rnd);
        return items;
    }

    public static List<ItemModel> getPastries(Context ctx) {
        List<ItemModel> items = new ArrayList<>();
        TypedArray img_p = ctx.getResources().obtainTypedArray(R.array.img_pastries);
        String[] name_p = ctx.getResources().getStringArray(R.array.str_pastries);
        String[] prc_p = ctx.getResources().getStringArray(R.array.prc_pastries);
        List<Integer> img_p_list = convertToInt(img_p);
        List<String> name_p_list = Arrays.asList(name_p);
        List<String> prc_p_list = Arrays.asList(prc_p);
        for (int i = 0; i < img_p_list.size(); i++) {
            ItemModel item = new ItemModel(Long.parseLong("2" + i), img_p_list.get(i), name_p_list.get(i), Long.parseLong(prc_p_list.get(i)), ctx.getString(R.string.menu_cat2));
            items.add(item);
        }
        Collections.shuffle(items, rnd);
        return items;
    }

    public static List<ItemModel> getCakes(Context ctx) {
        List<ItemModel> items = new ArrayList<>();
        TypedArray img_c = ctx.getResources().obtainTypedArray(R.array.img_cakes);
        String[] name_c = ctx.getResources().getStringArray(R.array.str_cakes);
        String[] prc_c = ctx.getResources().getStringArray(R.array.prc_cakes);
        List<Integer> img_c_list = convertToInt(img_c);
        List<String> name_c_list = Arrays.asList(name_c);
        List<String> prc_c_list = Arrays.asList(prc_c);
        for (int i = 0; i < img_c_list.size(); i++) {
            ItemModel item = new ItemModel(Long.parseLong("3" + i), img_c_list.get(i), name_c_list.get(i), Long.parseLong(prc_c_list.get(i)), ctx.getString(R.string.menu_cat3));
            items.add(item);
        }
        Collections.shuffle(items, rnd);
        return items;
    }

    public static List<ItemModel> getBiscuits(Context ctx) {
        List<ItemModel> items = new ArrayList<>();
        TypedArray img_b = ctx.getResources().obtainTypedArray(R.array.img_biscuits);
        String[] name_b = ctx.getResources().getStringArray(R.array.str_biscuits);
        String[] prc_b = ctx.getResources().getStringArray(R.array.prc_biscuits);
        List<Integer> img_b_list = convertToInt(img_b);
        List<String> name_b_list = Arrays.asList(name_b);
        List<String> prc_b_list = Arrays.asList(prc_b);
        for (int i = 0; i < img_b_list.size(); i++) {
            ItemModel item = new ItemModel(Long.parseLong("4" + i), img_b_list.get(i), name_b_list.get(i), Long.parseLong(prc_b_list.get(i)), ctx.getString(R.string.menu_cat4));
            items.add(item);
        }
        Collections.shuffle(items, rnd);
        return items;
    }

    private static List<Integer> convertToInt(TypedArray arr) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            data.add(arr.getResourceId(i, -1));
        }
        return data;
    }
}

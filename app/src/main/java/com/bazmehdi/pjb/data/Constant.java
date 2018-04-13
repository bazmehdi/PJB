package com.bazmehdi.pjb.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import com.bazmehdi.pjb.R;
import com.bazmehdi.pjb.model.ItemModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@SuppressWarnings("ResourceType")
public class Constant {
    public static float getAPIVerison() {

        Float f = null;
        try {
            StringBuilder strBuild = new StringBuilder();
            strBuild.append(android.os.Build.VERSION.RELEASE.substring(0, 2));
            f = new Float(strBuild.toString());
        } catch (NumberFormatException e) {
            Log.e("", "erro ao recuperar a versão da API" + e.getMessage());
        }

        return f.floatValue();
    }

    private static Random rnd = new Random();
    public static List<ItemModel> getItemCat1(Context ctx) {
        List<ItemModel> items = new ArrayList<>();


        TypedArray img_cat1 = ctx.getResources().obtainTypedArray(R.array.item_cat1);
        String[] name = ctx.getResources().getStringArray(R.array.str_cat1);
        String[] prc = ctx.getResources().getStringArray(R.array.prc_cat1);
        List<Integer> img_cat_list = Arrays.asList(img_cat1);
        List<String> name_list = Arrays.asList(name);
        List<String> prc_list = Arrays.asList(prc);
        //List<Integer> img_mix = mixImg(img_c_f, img_c_m);
        //List<String> name_mix = mixStr(name_f, name_m);
        //List<String> prc_mix = mixStr(prc_f, prc_m);
        for (int i = 0; i < img_cat1.length() ; i++) {
            ItemModel item = new ItemModel( Long.parseLong("1"+i), img_cat1.get(i), name_list.get(i), Long.parseLong(prc_list.get(i)), ctx.getString(R.string.menu_cat1));
            items.add(item);
        }
        Collections.shuffle(items, rnd);
        return items;
    }

    private static List<Integer> mixImg(TypedArray f_arr, TypedArray s_arr) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < f_arr.length(); i++) {
            data.add(f_arr.getResourceId(i, -1));
        }
        for (int i = 0; i < s_arr.length(); i++) {
            data.add(s_arr.getResourceId(i, -1));
        }
        return data;
    }
    private static List<String> mixStr(String[] f_str, String[] s_str) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < f_str.length; i++) {
            data.add(f_str[i]);
        }
        for (int i = 0; i < s_str.length; i++) {
            data.add(s_str[i]);
        }
        return data;
    }

    private static int getRandomIndex(Random r, int min, int max) {
        return r.nextInt(max - min) + min;
    }
    private static long getRandomLikes(){
        return getRandomIndex(rnd, 10, 250);
    }
    public static String getRandomSales(){
        return getRandomIndex(rnd, 2, 1000) +" Sales";
    }
    public static String getRandomReviews(){
        return getRandomIndex(rnd, 0, 800)+" Reviews";
    }}
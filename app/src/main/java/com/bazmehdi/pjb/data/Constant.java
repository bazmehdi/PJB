package com.bazmehdi.pjb.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import com.bazmehdi.pjb.R;
import com.bazmehdi.pjb.model.ItemModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
        TypedArray img_c_m = ctx.getResources().obtainTypedArray(R.array.item_clothing_male);
        String[] name_m = ctx.getResources().getStringArray(R.array.str_clothing_male);
        String[] prc_m = ctx.getResources().getStringArray(R.array.prc_clothing_male);
        List<Integer> img_mix = mixImg(img_c_f, img_c_m);
        List<String> name_mix = mixStr(name_f, name_m);
        List<String> prc_mix = mixStr(prc_f, prc_m);
        for (int i = 0; i < img_mix.size() ; i++) {
            ItemModel item = new ItemModel( Long.parseLong("1"+i), img_mix.get(i), name_mix.get(i), Long.parseLong(prc_mix.get(i)), ctx.getString(R.string.menu_clothing), getRandomLikes());
            items.add(item);
        }
        Collections.shuffle(items, rnd);
        return items;
    }
    public static List<ItemModel> getItemCat2(Context ctx) {
        List<ItemModel> items = new ArrayList<>();
        TypedArray img_c_f = ctx.getResources().obtainTypedArray(R.array.item_shoes_female);
        TypedArray img_c_m = ctx.getResources().obtainTypedArray(R.array.item_shoes_male);
        String[] name_f = ctx.getResources().getStringArray(R.array.str_shoes_female);
        String[] name_m = ctx.getResources().getStringArray(R.array.str_shoes_male);
        String[] prc_f = ctx.getResources().getStringArray(R.array.prc_shoes_female);
        String[] prc_m = ctx.getResources().getStringArray(R.array.prc_shoes_male);
        List<String> name_mix = mixStr(name_f, name_m);
        List<String> prc_mix = mixStr(prc_f, prc_m);
        List<Integer> img_mix = mixImg(img_c_f, img_c_m);
        for (int i = 0; i < img_mix.size() ; i++) {
            ItemModel item = new ItemModel( Long.parseLong("2"+i), img_mix.get(i), name_mix.get(i), Long.parseLong(prc_mix.get(i)), ctx.getString(R.string.menu_shoes), getRandomLikes());
            items.add(item);
        }
        Collections.shuffle(items, rnd);
        return items;
    }
    public static List<ItemModel> getItemCat3(Context ctx) {
        List<ItemModel> items = new ArrayList<>();
        TypedArray img_c_f = ctx.getResources().obtainTypedArray(R.array.item_watches_female);
        TypedArray img_c_m = ctx.getResources().obtainTypedArray(R.array.item_watches_male);
        String[] name_f = ctx.getResources().getStringArray(R.array.str_watches_female);
        String[] name_m = ctx.getResources().getStringArray(R.array.str_watches_male);
        String[] prc_f = ctx.getResources().getStringArray(R.array.prc_watches_female);
        String[] prc_m = ctx.getResources().getStringArray(R.array.prc_watches_male);
        List<String> name_mix = mixStr(name_f, name_m);
        List<String> prc_mix = mixStr(prc_f, prc_m);
        List<Integer> img_mix = mixImg(img_c_f, img_c_m);
        for (int i = 0; i < img_mix.size() ; i++) {
            ItemModel item = new ItemModel( Long.parseLong("3"+i), img_mix.get(i), name_mix.get(i), Long.parseLong(prc_mix.get(i)), ctx.getString(R.string.menu_watches), getRandomLikes());
            items.add(item);
        }
        Collections.shuffle(items, rnd);
        return items;
    }
    public static List<ItemModel> getItemCat4(Context ctx) {
        List<ItemModel> items = new ArrayList<>();
        TypedArray img_c_f = ctx.getResources().obtainTypedArray(R.array.item_acc_female);
        TypedArray img_c_m = ctx.getResources().obtainTypedArray(R.array.item_acc_male);
        String[] name_f = ctx.getResources().getStringArray(R.array.str_acc_female);
        String[] name_m = ctx.getResources().getStringArray(R.array.str_acc_male);
        String[] prc_f = ctx.getResources().getStringArray(R.array.prc_acc_female);
        String[] prc_m = ctx.getResources().getStringArray(R.array.prc_acc_male);
        List<String> name_mix = mixStr(name_f, name_m);
        List<String> prc_mix = mixStr(prc_f, prc_m);
        List<Integer> img_mix = mixImg(img_c_f, img_c_m);
        for (int i = 0; i < img_mix.size() ; i++) {
            ItemModel item = new ItemModel( Long.parseLong("4"+i), img_mix.get(i), name_mix.get(i), Long.parseLong(prc_mix.get(i)), ctx.getString(R.string.menu_accessories), getRandomLikes());
            items.add(item);
        }
        Collections.shuffle(items, rnd);
        return items;
    }
    public static List<ItemModel> getItemCat5(Context ctx) {
        List<ItemModel> items = new ArrayList<>();
        TypedArray img_c_f = ctx.getResources().obtainTypedArray(R.array.item_bags_female);
        TypedArray img_c_m = ctx.getResources().obtainTypedArray(R.array.item_bags_male);
        String[] name_f = ctx.getResources().getStringArray(R.array.str_bags_female);
        String[] name_m = ctx.getResources().getStringArray(R.array.str_bags_male);
        String[] prc_f = ctx.getResources().getStringArray(R.array.prc_bags_female);
        String[] prc_m = ctx.getResources().getStringArray(R.array.prc_bags_male);
        List<String> name_mix = mixStr(name_f, name_m);
        List<String> prc_mix = mixStr(prc_f, prc_m);
        List<Integer> img_mix = mixImg(img_c_f, img_c_m);
        for (int i = 0; i < img_mix.size(); i++) {
            ItemModel item = new ItemModel(Long.parseLong("5" + i), img_mix.get(i), name_mix.get(i), Long.parseLong(prc_mix.get(i)), ctx.getString(R.string.menu_bags), getRandomLikes());
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
    }
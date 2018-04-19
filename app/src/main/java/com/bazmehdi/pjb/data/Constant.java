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

@SuppressWarnings("ResourceType")
public class Constant {

    public static float getAPIVerison() {

        Float f = null;
        try {
            StringBuilder strBuild = new StringBuilder();
            strBuild.append(android.os.Build.VERSION.RELEASE.substring(0, 2));
            f = new Float(strBuild.toString());
        } catch (NumberFormatException e) {
            Log.e("", "Error retrieving API version" + e.getMessage());
        }

        return f.floatValue();
    }

    private static Random rnd = new Random();

    public static List<ItemModel> getSavoury(Context ctx) {
        List<ItemModel> items = new ArrayList<>();
        TypedArray img_s = ctx.getResources().obtainTypedArray((R.array.img_savoury));
        String[] name_s = ctx.getResources().getStringArray(R.array.str_savoury);
        String[] prc_s = ctx.getResources().getStringArray(R.array.prc_savoury);
        List<Integer> img_s_list = convertToInt(img_s);
        List<String> name_s_list = Arrays.asList(name_s);
        List<String> prc_s_list = Arrays.asList(prc_s);
        for (int i = 0; i < img_s_list.size(); i++) {
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

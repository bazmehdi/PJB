package com.bazmehdi.pjb;

import android.util.Log;

import com.bazmehdi.pjb.fragment.CategoryFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class FoodListener implements ChildEventListener {

    CategoryFragment owner;

    public ArrayList<String> biscuitsNameArray = new ArrayList<>();
    public ArrayList<String> biscuitsPrcArray = new ArrayList<>();
    public ArrayList<Integer> biscuitsImgArray = new ArrayList<>();

    public FoodListener(CategoryFragment o) {

        owner = o;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        for (DataSnapshot childSnapshot : dataSnapshot.child("Biscuits").getChildren()) {
            biscuitsNameArray.add(childSnapshot.child("itemName").getValue().toString());
            biscuitsPrcArray.add(childSnapshot.child("itemPrc").getValue().toString());
            biscuitsImgArray.add(Integer.parseInt(childSnapshot.child("itemImg").getValue().toString()));
        }

        Log.d("names list", owner.biscuitsNameArray.toString());
        Log.d("price list", owner.biscuitsPrcArray.toString());
        Log.d("image list", owner.biscuitsImgArray.toString());

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

        Log.e("Error","The read failed: " + databaseError.getCode());

    }

}

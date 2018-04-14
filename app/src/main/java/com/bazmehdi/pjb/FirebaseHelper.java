package com.bazmehdi.pjb;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.bazmehdi.pjb.model.ItemModel;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    ArrayList<String> items = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //READ
    public ArrayList<String> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

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

        return items;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        items.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            String name = ds.getValue(ItemModel.class).getName();
            String category = ds.getValue(ItemModel.class).getCategory();
            String price = Long.toString(ds.getValue(ItemModel.class).getPrice());
            String image = Integer.toString(ds.getValue(ItemModel.class).getImg());
            items.add(name);
            items.add(price);
            items.add(category);
            items.add(image);
        }
    }
}
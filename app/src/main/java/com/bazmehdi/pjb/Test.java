package com.bazmehdi.pjb;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bazmehdi.pjb.adapter.ItemsListAdapter;
import com.bazmehdi.pjb.model.ItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Test extends AppCompatActivity {

    private static final String TAG = "Test";

    private DatabaseReference db;
    private RecyclerView listViewItems;
    List<ItemModel> itemList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        db = FirebaseDatabase.getInstance().getReference("products");
        listViewItems = findViewById(R.id.listViewItems);
        itemList = new ArrayList<>();

        listViewItems.setLayoutManager(new LinearLayoutManager(context));
        ItemsListAdapter adapter = new ItemsListAdapter(com.bazmehdi.pjb.Test.this, itemList);
        listViewItems.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                itemList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    ItemModel item = itemSnapshot.getValue(ItemModel.class);

                    itemList.add(item);
                    Log.d(TAG, "Value is: " + item);
                }

                ItemsListAdapter adapter = new ItemsListAdapter(com.bazmehdi.pjb.Test.this, itemList);
                listViewItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

}
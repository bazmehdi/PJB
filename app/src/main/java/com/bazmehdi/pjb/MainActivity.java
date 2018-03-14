package com.bazmehdi.pjb;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Baz on 14/03/2018.
 */

public class MainActivity extends AppCompatActivity {

    private String mDisplayName;
    private DatabaseReference memberDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Set up the display name and get the Firebase reference
        setupDisplayName();
        memberDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }

    // TODO: Retrieve the display name from the Shared Preferences
    private void setupDisplayName(){

        SharedPreferences prefs = getSharedPreferences(Register.APP_PREFS, MODE_PRIVATE);

        mDisplayName = prefs.getString(Register.DISPLAY_NAME_KEY, null);

        if (mDisplayName == null) mDisplayName = "Anonymous";
    }

}

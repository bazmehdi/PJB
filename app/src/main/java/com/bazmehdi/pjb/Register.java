package com.bazmehdi.pjb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Baz on 13/03/2018.
 */

public class Register extends AppCompatActivity {

    static final String APP_PREFS = "AppPrefs";
    static final String DISPLAY_NAME_KEY = "username";

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView memberUsernameView;
    private AutoCompleteTextView memberEmailView;
    private EditText memberPasswordView;
    private EditText memberConfirmPasswordView;

    // Firebase instance variables
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        memberUsernameView = (AutoCompleteTextView) findViewById(R.id.register_username);
        memberEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        memberPasswordView = (EditText) findViewById(R.id.register_password);
        memberConfirmPasswordView = (EditText) findViewById(R.id.register_confirm_password);

        // Keyboard sign in action
        memberConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        // TODO: Get hold of an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

    }

    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        memberEmailView.setError(null);
        memberPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = memberEmailView.getText().toString();
        String password = memberPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            memberPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = memberPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            memberEmailView.setError(getString(R.string.error_field_required));
            focusView = memberEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            memberEmailView.setError(getString(R.string.error_invalid_email));
            focusView = memberEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();

        }
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        String confirmPassword = memberConfirmPasswordView.getText().toString();
        return confirmPassword.equals(password) && password.length() > 4;
    }

    private void createFirebaseUser() {
        String email = memberEmailView.getText().toString();
        String password = memberPasswordView.getText().toString();



        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("PaajeeBakers", "createUser onComplete: " + task.isSuccessful());

                        if(!task.isSuccessful()){
                            Log.d("PaajeeBakers", "user creation failed");
                            showErrorDialog("Registration attempt failed");
                        } else {
                            saveDisplayName();
                            Intent intent = new Intent(Register.this, Login.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                });
    }

    // TODO: Save the display name to Shared Preferences
    private void saveDisplayName() {
        String displayName = memberUsernameView.getText().toString();
        SharedPreferences prefs = getSharedPreferences(APP_PREFS, 0);
        prefs.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }

    // TODO: Create an alert dialog to show in case registration failed
    private void showErrorDialog(String message){

        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

}

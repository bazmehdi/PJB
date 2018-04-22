package com.bazmehdi.pjb.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.bazmehdi.pjb.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private Button forgotPasswordBtn;

    // Firebase instance variable
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mEmailView = findViewById(R.id.forgot_password_email);
        forgotPasswordBtn = findViewById(R.id.forgot_password_btn);


        // Gets hold of an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailView.getText().toString();

                // Checks if field is empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Please enter the email you registered with", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sends email to user using sendPasswordResetEmail from Firebase libraries
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPassword.this, Login.class);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgotPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

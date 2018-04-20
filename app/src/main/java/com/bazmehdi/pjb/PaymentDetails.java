package com.bazmehdi.pjb;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    TextView paymentId, paymentAmount, paymentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_details);

        paymentId = findViewById(R.id.paymentId);
        paymentAmount = findViewById(R.id.paymentAmount);
        paymentStatus = findViewById(R.id.paymentStatus);

        //Get Intent
        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showDetails(JSONObject response, String pAmount) {

        try {
            paymentId.setText(response.getString("id"));
            paymentAmount.setText("£" + pAmount);
            paymentStatus.setText(response.getString("state"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

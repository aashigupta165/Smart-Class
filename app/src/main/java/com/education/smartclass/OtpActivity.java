package com.education.smartclass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OtpActivity extends AppCompatActivity {

    private TextView verifybtn;
    private EditText otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otp = findViewById(R.id.otp);

        verifybtn = findViewById(R.id.verifybtn);
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                if (bundle.getInt("OTP") == Integer.valueOf(otp.getText().toString())) {
                    Toast.makeText(OtpActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OtpActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
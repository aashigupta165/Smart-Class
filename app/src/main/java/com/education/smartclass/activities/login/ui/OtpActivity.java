package com.education.smartclass.activities.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.education.smartclass.R;

public class OtpActivity extends AppCompatActivity {

    private TextView verifybtn, resendbtn;
    private EditText otp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otp = findViewById(R.id.otp);
        resendbtn = findViewById(R.id.resendbtn);
        progressDialog = new ProgressDialog(this);

        verifybtn = findViewById(R.id.verifybtn);
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Verifying...");
                progressDialog.show();

                Intent intent = new Intent(OtpActivity.this, ResetPasswordActivity.class);
                startActivity(intent);

                progressDialog.dismiss();
            }
        });

        resendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Resending...");
                progressDialog.show();

                progressDialog.dismiss();
            }
        });
    }
}
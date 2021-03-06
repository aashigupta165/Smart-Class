package com.education.smartclass.modules.login.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.modules.login.model.SendOtpViewModel;
import com.education.smartclass.modules.login.model.VerifyOtpViewModel;
import com.education.smartclass.utils.SnackBar;

public class OtpActivity extends AppCompatActivity {

    private TextView message, verifybtn, resendbtn;
    private EditText otp;
    private ProgressDialog progressDialog;

    private RelativeLayout relativeLayout;

    private SendOtpViewModel sendOtpViewModel;
    private VerifyOtpViewModel verifyOtpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        message = findViewById(R.id.message);
        otp = findViewById(R.id.otp);
        resendbtn = findViewById(R.id.resendbtn);

        message.setText("Enter the OTP sent to " + getIntent().getStringExtra("email"));

        relativeLayout = findViewById(R.id.relativeLayout);

        progressDialog = new ProgressDialog(this);

        DataObserver();
        ButtonClickEvents();
    }

    private void ButtonClickEvents() {

        verifybtn = findViewById(R.id.verifybtn);
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(verifybtn.getWindowToken(), 0);

                if (otp.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Enter Otp!");
                    return;
                }

                progressDialog.setMessage("Verifying...");
                progressDialog.show();

                verifyOtpViewModel.verifyOtp(otp.getText().toString(), getIntent().getStringExtra("email"), getIntent().getStringExtra("mobile"),
                        getIntent().getStringExtra("firstName"));
            }
        });

        resendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(resendbtn.getWindowToken(), 0);

                progressDialog.setMessage("Resending...");
                progressDialog.show();

                sendOtpViewModel.sendOtp(getIntent().getStringExtra("email"), getIntent().getStringExtra("mobile"), getIntent().getStringExtra("firstName"));
            }
        });
    }

    private void DataObserver() {

        sendOtpViewModel = ViewModelProviders.of(this).get(SendOtpViewModel.class);
        LiveData<String> message = sendOtpViewModel.getMessage();

        message.observe(OtpActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "otp_sent":
                        new SnackBar(relativeLayout, "OTP Sent!");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Try Again Later!");
                }
            }
        });

        verifyOtpViewModel = ViewModelProviders.of(this).get(VerifyOtpViewModel.class);
        LiveData<String> msg = verifyOtpViewModel.getMessage();

        msg.observe(OtpActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "matched":
                        new SnackBar(relativeLayout, "OTP Verified.");
                        Intent intent = new Intent(OtpActivity.this, ResetPasswordActivity.class);
                        intent.putExtra("email", getIntent().getStringExtra("email"));
                        intent.putExtra("mobile", getIntent().getStringExtra("mobile"));
                        intent.putExtra("firstName", getIntent().getStringExtra("firstName"));
                        startActivity(intent);
                        break;
                    case "not_matched":
                        new SnackBar(relativeLayout, "Wrong OTP.");
                        break;
                    case "otp_expired":
                        new SnackBar(relativeLayout, "OTP Expired!");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Try Again Later!");
                }
            }
        });
    }
}
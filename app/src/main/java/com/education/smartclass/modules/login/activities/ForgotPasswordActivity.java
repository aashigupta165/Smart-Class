package com.education.smartclass.modules.login.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.modules.login.model.SendOtpViewModel;
import com.education.smartclass.utils.SnackBar;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextView nextbtn;
    private EditText mobile, email, firstName;

    private RelativeLayout relativeLayout;

    private SendOtpViewModel sendOtpViewModel;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        firstName = findViewById(R.id.firstName);
        nextbtn = findViewById(R.id.nextbtn);

        progressDialog = new ProgressDialog(this);

        relativeLayout = findViewById(R.id.relativeLayout);

        DataObserver();
        buttonClickEvents();
    }

    private void buttonClickEvents() {

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nextbtn.getWindowToken(), 0);

                if (email.getText().toString().equals("") || mobile.getText().toString().equals("") || firstName.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Enter The Details!");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    new SnackBar(relativeLayout, "Please Enter Valid Email!");
                    return;
                }

                progressDialog.setMessage("Loading...");
                progressDialog.show();

                sendOtpViewModel.sendOtp(email.getText().toString(), mobile.getText().toString(), firstName.getText().toString());
            }
        });
    }

    private void DataObserver() {

        sendOtpViewModel = ViewModelProviders.of(this).get(SendOtpViewModel.class);
        LiveData<String> message = sendOtpViewModel.getMessage();

        message.observe(ForgotPasswordActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "otp_sent":
                        new SnackBar(relativeLayout, "OTP Sent!");
                        Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                        intent.putExtra("email", email.getText().toString());
                        intent.putExtra("mobile", mobile.getText().toString());
                        intent.putExtra("firstName", firstName.getText().toString());
                        startActivity(intent);
                        break;
                    case "user_not_found":
                        new SnackBar(relativeLayout, "Invalid Credentials");
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
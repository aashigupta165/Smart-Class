package com.education.smartclass.roles.login.activities;

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
import com.education.smartclass.roles.login.model.ResetPasswordViewModel;
import com.education.smartclass.utils.SnackBar;

import java.util.regex.Pattern;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText password, confirmPassword;
    private TextView submitbtn;

    private RelativeLayout relativeLayout;

    private ResetPasswordViewModel resetPasswordViewModel;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        submitbtn = findViewById(R.id.submitbtn);

        relativeLayout = findViewById(R.id.relativeLayout);

        progressDialog = new ProgressDialog(this);

        dataObserver();
        buttonClickEvents();
    }

    private void dataObserver() {

        resetPasswordViewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel.class);
        LiveData<String> message = resetPasswordViewModel.getMessage();

        message.observe(ResetPasswordActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "password_changed":
                        new SnackBar(relativeLayout, "Password Changed Successfully!");
                        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    case "invalid_data":
                        new SnackBar(relativeLayout, "Invalid Data.");
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

    private void buttonClickEvents() {

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(submitbtn.getWindowToken(), 0);

                if (password.getText().toString().equals("") || confirmPassword.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Fill All Details!");
                    return;
                }

                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    new SnackBar(relativeLayout, "Password should be equal to Confirm Password");
                    return;
                }

                Pattern PASSWORD_PATTERN = Pattern.compile("^" + "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{8,}" + "$");

                if (!PASSWORD_PATTERN.matcher(password.getText().toString()).matches()) {
                    new SnackBar(relativeLayout, "Password Too Weak!");
                    return;
                }

                progressDialog.setMessage("Loading");
                progressDialog.show();

                resetPasswordViewModel.resetPassword(getIntent().getStringExtra("email"), password.getText().toString(), getIntent().getStringExtra("mobile"),
                        getIntent().getStringExtra("firstName"));
            }
        });
    }
}
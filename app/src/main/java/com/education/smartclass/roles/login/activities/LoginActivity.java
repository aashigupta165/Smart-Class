package com.education.smartclass.roles.login.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.roles.admin.activities.AdminHomeActivity;
import com.education.smartclass.R;
import com.education.smartclass.roles.login.model.LoginViewModel;
import com.education.smartclass.roles.Organisation.activities.OrganisationActivity;
import com.education.smartclass.roles.student.activities.StudentActivity;
import com.education.smartclass.roles.teacher.activities.TeacherActivity;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private EditText mobile, password;
    private TextView loginbtn, forgotPassword;

    private ProgressDialog progressBar;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        relativeLayout = findViewById(R.id.relativeLayout);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        forgotPassword = findViewById(R.id.forgotPassword);
        progressBar = new ProgressDialog(this);

        dataObserver();
        buttonClickEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            roleSelection();
        }
    }

    private void buttonClickEvents() {

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void dataObserver() {

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        LiveData<String> message = loginViewModel.getMessage();

        message.observe(LoginActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.dismiss();
                switch (s) {
                    case "loggedIn":
                        roleSelection();
                    case "Invalid_PhoneNo":
                        new SnackBar(relativeLayout, "Invalid Mobile Number");
                        break;
                    case "Invalid_Password":
                        new SnackBar(relativeLayout, "Invalid Password");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });
    }

    private void login() {

        Pattern PASSWORD_PATTERN = Pattern.compile("^" + "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{8,}" + "$");

        String Mobile = mobile.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if (Mobile.isEmpty() || Password.isEmpty()) {
            new SnackBar(relativeLayout, "Please Enter All The Details");
            return;
        }

        if (!PASSWORD_PATTERN.matcher(Password).matches() || Mobile.length() != 10) {
            new SnackBar(relativeLayout, "Invalid Credentials");
            return;
        }

        progressBar.setMessage("Loading...");
        progressBar.show();
        loginViewModel.dataRetrieval(getApplicationContext(), mobile.getText().toString(), password.getText().toString());
    }

    private void roleSelection() {
        Intent intent;
        switch (SharedPrefManager.getInstance(this).getUser().getRole()) {
            case "Admin":
                intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case "Organisation":
                intent = new Intent(LoginActivity.this, OrganisationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case "Teacher":
                intent = new Intent(LoginActivity.this, TeacherActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case "Student":
                intent = new Intent(LoginActivity.this, StudentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
}
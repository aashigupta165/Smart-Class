package com.education.smartclass.activities.admin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.utils.SnackBar;

import java.util.regex.Pattern;

public class RegisterNewOrgActivity1 extends AppCompatActivity {

    private EditText email, mobile;
    private TextView nextbtn;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_new_org_1);

        relativeLayout = findViewById(R.id.relativeLayout);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        nextbtn = findViewById(R.id.nextbtn);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });
    }

    private void checkDetails() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String Email = email.getText().toString().trim();
        String Mobile = mobile.getText().toString().trim();

        if (Email.isEmpty() || Mobile.isEmpty()) {
            new SnackBar(relativeLayout, "Please Enter All The Details");
            return;
        }

        if (!Email.matches(emailPattern) || Mobile.length() != 10) {
            new SnackBar(relativeLayout, "Invalid Credentials");
            return;
        }

        Intent intent = new Intent(RegisterNewOrgActivity1.this, RegisterNewOrgActivity2.class);
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("mobile", mobile.getText().toString());
        startActivity(intent);
    }
}
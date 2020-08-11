package com.education.smartclass.roles.Organisation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.utils.SnackBar;

public class ManualTeacherRegisterActivity1 extends AppCompatActivity {

    private EditText email, mobile;
    private TextView nextbtn;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_teacher_register1);

        relativeLayout = findViewById(R.id.relativeLayout);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        nextbtn = findViewById(R.id.nextbtn);

        buttonClickEvents();
    }

    private void buttonClickEvents() {

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

        Intent intent = new Intent(ManualTeacherRegisterActivity1.this, ManualTeacherRegisterActivity2.class);
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("mobile", mobile.getText().toString());
        startActivity(intent);
    }
}
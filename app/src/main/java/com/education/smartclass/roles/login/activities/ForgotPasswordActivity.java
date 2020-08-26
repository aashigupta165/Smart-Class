package com.education.smartclass.roles.login.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import com.education.smartclass.utils.SnackBar;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextView nextbtn;
    private EditText email;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.email);

        relativeLayout = findViewById(R.id.relativeLayout);

        nextbtn = findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nextbtn.getWindowToken(), 0);

                if (email.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Enter Your Email!");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    new SnackBar(relativeLayout, "Please Enter Valid Email!");
                    return;
                }

                Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                intent.putExtra("email", email.getText().toString());
                startActivity(intent);
            }
        });
    }
}
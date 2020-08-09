package com.education.smartclass.activities.admin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.education.smartclass.R;

public class RegisterNewOrgActivity1 extends AppCompatActivity {

    private EditText email, mobile;
    private TextView nextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_new_org_1);

        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        nextbtn = findViewById(R.id.nextbtn);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterNewOrgActivity1.this, RegisterNewOrgActivity2.class);
                intent.putExtra("email", email.getText().toString());
                intent.putExtra("mobile", mobile.getText().toString());
                startActivity(intent);
            }
        });
    }
}
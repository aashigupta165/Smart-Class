package com.education.smartclass.activities.admin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.education.smartclass.R;

public class CreateNewOrgAdminActivity2 extends AppCompatActivity {

    TextView nextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_new_org_2);

        nextbtn = findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewOrgAdminActivity2.this, CreateNewOrgAdminActivity3.class);
                startActivity(intent);
            }
        });
    }
}
package com.education.smartclass.activities.admin.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.education.smartclass.R;

public class RegisterNewOrgActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_new_org_3);

        Bundle bundle = getIntent().getExtras();
        Toast.makeText(this, bundle.getString("email") + bundle.getString("mobile") + bundle.getString("orgName")
        + bundle.getString("orgCode") + bundle.getString("orgType") + bundle.getString("orgAddress"), Toast.LENGTH_LONG).show();
    }
}
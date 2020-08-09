package com.education.smartclass.activities.admin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.education.smartclass.R;

public class RegisterNewOrgActivity2 extends AppCompatActivity {

    private EditText orgName, orgCode, orgType, orgAddress;
    private TextView nextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_new_org_2);

        orgName = findViewById(R.id.org_Name);
        orgCode = findViewById(R.id.org_Code);
        orgType = findViewById(R.id.org_Type);
        orgAddress = findViewById(R.id.org_Address);
        nextbtn = findViewById(R.id.nextbtn);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getIntent().getExtras();

                Intent intent = new Intent(RegisterNewOrgActivity2.this, RegisterNewOrgActivity3.class);
                intent.putExtra("email", bundle.getString("email"));
                intent.putExtra("mobile", bundle.getString("mobile"));
                intent.putExtra("orgName", orgName.getText().toString());
                intent.putExtra("orgCode", orgCode.getText().toString());
                intent.putExtra("orgType", orgType.getText().toString());
                intent.putExtra("orgAddress", orgAddress.getText().toString());
                startActivity(intent);
            }
        });
    }
}
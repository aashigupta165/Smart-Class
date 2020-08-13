package com.education.smartclass.roles.admin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.education.smartclass.R;
import com.education.smartclass.utils.SnackBar;

public class RegisterNewOrgActivity2 extends AppCompatActivity {

    private EditText orgName, orgCode, orgType, orgAddress;
    private TextView nextbtn;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_new_org_2);

        relativeLayout = findViewById(R.id.relativeLayout);
        orgName = findViewById(R.id.org_Name);
        orgCode = findViewById(R.id.org_Code);
        orgType = findViewById(R.id.org_Type);
        orgAddress = findViewById(R.id.org_Address);
        nextbtn = findViewById(R.id.nextbtn);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });
    }

    private void checkDetails() {

        Bundle bundle = getIntent().getExtras();

        String OrgName = orgName.getText().toString().trim();
        String OrgCode = orgCode.getText().toString().trim();
        String OrgType = orgType.getText().toString().trim();
        String OrgAddress = orgAddress.getText().toString().trim();

        if (OrgName.isEmpty() || OrgCode.isEmpty() || OrgType.isEmpty() || OrgAddress.isEmpty()) {
            new SnackBar(relativeLayout, "Please Enter All The Details");
            return;
        }

        Intent intent = new Intent(RegisterNewOrgActivity2.this, RegisterNewOrgActivity3.class);
        intent.putExtra("email", bundle.getString("email"));
        intent.putExtra("mobile", bundle.getString("mobile"));
        intent.putExtra("orgName", OrgName);
        intent.putExtra("orgCode", OrgCode);
        intent.putExtra("orgType", OrgType);
        intent.putExtra("orgAddress", OrgAddress);
        startActivity(intent);
    }
}
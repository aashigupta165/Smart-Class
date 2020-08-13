package com.education.smartclass.roles.Organisation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.roles.admin.ui.RegisterNewOrgActivity2;
import com.education.smartclass.roles.admin.ui.RegisterNewOrgActivity3;
import com.education.smartclass.utils.SnackBar;

public class ManualTeacherRegisterActivity2 extends AppCompatActivity {

    private EditText teacherName, teacherAge, teacherDesignation, teacherCode;
    private RadioGroup teacherGender;
    private RadioButton radioButton;
    String gender = "male";
    private TextView nextbtn;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_teacher_register2);

        teacherName = findViewById(R.id.teacher_Name);
        teacherAge = findViewById(R.id.teacher_Age);
        teacherDesignation = findViewById(R.id.teacher_Designation);
        teacherCode = findViewById(R.id.teacher_Code);
        teacherGender = findViewById(R.id.teacher_gender);
        nextbtn = findViewById(R.id.nextbtn);

        relativeLayout = findViewById(R.id.relativeLayout);

        teacherGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
                if (radioButton.getText().toString().equals("Male")){
                    gender = "male";
                }else{
                    gender = "female";
                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });
    }

    private void checkDetails() {

        String TeacherName = teacherName.getText().toString().trim();
        String TeacherAge = teacherAge.getText().toString().trim();
        String TeacherDesignation = teacherDesignation.getText().toString().trim();
        String TeacherCode = teacherCode.getText().toString().trim();

        if (TeacherName.isEmpty() || TeacherAge.isEmpty() || TeacherDesignation.isEmpty() || TeacherCode.isEmpty()) {
            new SnackBar(relativeLayout, "Please Enter All The Details.");
            return;
        }

        if(Integer.parseInt(TeacherAge)<18 || Integer.parseInt(TeacherAge)>100){
            new SnackBar(relativeLayout, "Please Enter Valid Details.");
            return;
        }

        Bundle bundle = getIntent().getExtras();

        Intent intent = new Intent(ManualTeacherRegisterActivity2.this, ManualTeacherRegisterActivity3.class);
        intent.putExtra("email", bundle.getString("email"));
        intent.putExtra("mobile", bundle.getString("mobile"));
        intent.putExtra("teacherName", TeacherName);
        intent.putExtra("teacherAge", TeacherAge);
        intent.putExtra("teacherDesignation", TeacherDesignation);
        intent.putExtra("teacherCode", TeacherCode);
        intent.putExtra("teacherGender", gender);
        startActivity(intent);
    }
}
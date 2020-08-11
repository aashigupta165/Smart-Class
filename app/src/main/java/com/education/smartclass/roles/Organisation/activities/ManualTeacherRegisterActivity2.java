package com.education.smartclass.roles.Organisation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.education.smartclass.R;

public class ManualTeacherRegisterActivity2 extends AppCompatActivity {

    private TextView nextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_teacher_register2);

        nextbtn = findViewById(R.id.nextbtn);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManualTeacherRegisterActivity2.this, ManualTeacherRegisterActivity3.class);
                startActivity(intent);
            }
        });
    }
}
package com.education.smartclass.roles.Organisation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.education.smartclass.R;

public class TeacherRegisterActivity extends AppCompatActivity {

    private TextView manual_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);

        manual_entry = findViewById(R.id.manual_entry);

        buttonClickEvents();
    }

    private void buttonClickEvents() {

        manual_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherRegisterActivity.this, ManualTeacherRegisterActivity1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}
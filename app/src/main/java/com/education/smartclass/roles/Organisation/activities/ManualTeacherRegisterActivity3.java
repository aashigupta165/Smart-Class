package com.education.smartclass.roles.Organisation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.education.smartclass.R;
import com.education.smartclass.roles.Organisation.views.ClassDetails;

public class ManualTeacherRegisterActivity3 extends AppCompatActivity {

    private GridLayout layout;
    Button addbtn;
    ClassDetails classDetails;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_teacher_register3);

        layout = findViewById(R.id.myLayout);
        addbtn = findViewById(R.id.addbtn);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classDetails = new ClassDetails(context);
                layout.addView(classDetails.descriptionTextView(getApplicationContext(), "Item No. 1"),3);
                layout.addView(classDetails.receiveQuantityEditText(getApplicationContext()), 4);
                layout.addView(classDetails.inStockTextView(getApplicationContext(), "34.00"),5);
            }
        });
    }
}
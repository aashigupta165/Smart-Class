package com.education.smartclass.roles.Organisation.views;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ClassDetails {

    Context ctx;

    public ClassDetails(Context ctx) {
        this.ctx = ctx;
    }

    public TextView descriptionTextView(Context context, String text){
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(10);
        textView.setTextColor(Color.rgb(0,0,0));
        textView.setText(" "+text+" ");
        textView.setMaxEms(8);
        return textView;
    }

    public EditText receiveQuantityEditText(Context context){
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText editText = new EditText(context);
        int id = 0;
        editText.setId(id);
        editText.setMinEms(2);
        editText.setTextColor(Color.rgb(0,0,0));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        return editText;
    }

    public TextView inStockTextView(Context context, String text){
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(123);
        textView.setText(text);
        return textView;
    }
}

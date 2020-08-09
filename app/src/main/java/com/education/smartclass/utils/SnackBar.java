package com.education.smartclass.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.google.android.material.snackbar.Snackbar;

public class SnackBar {
    public SnackBar(RelativeLayout relativeLayout, String message) {
        Snackbar snackbar = Snackbar.make(relativeLayout, message, Snackbar.LENGTH_LONG);
        View snackView = snackbar.getView();
        TextView textView = snackView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(15);
        snackbar.show();
    }
}

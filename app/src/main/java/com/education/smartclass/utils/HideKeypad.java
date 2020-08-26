package com.education.smartclass.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

public class HideKeypad {

    public HideKeypad(RelativeLayout relativeLayout, Context context){
        InputMethodManager imm = (InputMethodManager) ContextCompat.getSystemService(context, null);
//        imm.hideSoftInputFromWindow(.getWindowToken(), 0);
    }

}

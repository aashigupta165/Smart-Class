package com.education.smartclass.utils;

import android.content.Context;
import android.content.Intent;

import com.education.smartclass.modules.login.activities.LoginActivity;
import com.education.smartclass.storage.SharedPrefManager;

public class SessionExpire {

    public SessionExpire(Context context) {
        SharedPrefManager.getInstance(context).clear();
        Intent intent_logout = new Intent(context, LoginActivity.class);
        intent_logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent_logout);
    }
}

package com.education.smartclass.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.education.smartclass.models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }


    public void saveUser(User user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (user.getRole().equals("Admin")){
            editor.putString("name", user.getName());
            editor.putString("mobile", user.getMobile());
            editor.putString("email", user.getEmail());
            editor.putString("role", user.getRole());
        }
        else if(user.getRole().equals("Organisation")){
            editor.putString("orgName", user.getOrgName());
            editor.putString("orgCode", user.getOrgCode());
            editor.putString("orgType", user.getOrgType());
            editor.putString("orgAddress", user.getOrgAddress());
            editor.putString("orgLogo", user.getOrgLogo());
            editor.putString("orgEmail", user.getOrgEmail());
            editor.putString("role", user.getRole());
            editor.putString("orgMobile", user.getOrgMobile());
        }
        else if(user.getRole().equals("Teacher")){
            editor.putString("teacherName", user.getTeacherName());
            editor.putString("teacherAge", user.getTeacherAge());
            editor.putString("teacherDesignation", user.getTeacherDesignation());
            editor.putString("teacherCode", user.getTeacherCode());
            editor.putString("teacherGender", user.getTeacherGender());
            editor.putString("role", user.getRole());
            editor.putString("teacherEmail", user.getTeacherEmail());
            editor.putString("teacherMobile", user.getTeacherMobile());
        }
        else if (user.getRole().equals("Student")){
            editor.putString("studentName", user.getStudentName());
            editor.putString("studentRollNo", user.getStudentRollNo());
            editor.putString("studentClass", user.getStudentClass());
            editor.putString("studentSection", user.getStudentSection());
            editor.putString("studentFatherName", user.getStudentFatherName());
            editor.putString("role", user.getRole());
            editor.putString("studentEmail", user.getStudentEmail());
            editor.putString("studentMobile", user.getStudentMobile());
            editor.putString("studentDOB", user.getStudentDOB());
            editor.putString("studentGender", user.getStudentGender());
        }

        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new User(
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("mobile", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("role", null),
                sharedPreferences.getString("orgName", null),
                sharedPreferences.getString("orgCode", null),
                sharedPreferences.getString("orgType", null),
                sharedPreferences.getString("orgAddress", null),
                sharedPreferences.getString("orgLogo", null),
                sharedPreferences.getString("orgEmail", null),
                sharedPreferences.getString("orgMobile", null),
                sharedPreferences.getString("teacherName", null),
                sharedPreferences.getString("teacherAge", null),
                sharedPreferences.getString("teacherDesignation", null),
                sharedPreferences.getString("teacherCode", null),
                sharedPreferences.getString("teacherGender", null),
                sharedPreferences.getString("teacherEmail", null),
                sharedPreferences.getString("teacherMobile", null),
                sharedPreferences.getString("studentName", null),
                sharedPreferences.getString("studentRollNo", null),
                sharedPreferences.getString("studentClass", null),
                sharedPreferences.getString("studentSection", null),
                sharedPreferences.getString("studentFatherName", null),
                sharedPreferences.getString("studentEmail", null),
                sharedPreferences.getString("studentMobile", null),
                sharedPreferences.getString("studentDOB", null),
                sharedPreferences.getString("studentGender", null)
        );
    }

    public void saveToken(String token) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("token", token);

        editor.apply();

    }

    public String getPassword() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String password = sharedPreferences.getString("password", null);
        return password;
    }

    public void savePassword(String password) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("password", password);

        editor.apply();

    }

    public String getToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        return token;
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
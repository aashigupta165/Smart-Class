package com.education.smartclass.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.Holder;
import com.education.smartclass.holder.TeacherHolder;
import com.education.smartclass.models.Organisation;
import com.education.smartclass.models.Teachers;
import com.education.smartclass.roles.Organisation.model.StatusChangeViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.StatusUpdate;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherHolder> {

    Context c;
    ArrayList<Teachers> teacherList;

    public TeacherAdapter(Context c, ArrayList<Teachers> teacherList) {
        this.c = c;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_list_row, null);
        return new TeacherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherHolder holder, int position) {
        holder.teacherName.setText(teacherList.get(position).getTeacherName());
        holder.teacherCode.setText(teacherList.get(position).getTeacherCode());
        holder.switchCompat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String orgCode = SharedPrefManager.getInstance(c).getUser().getOrgCode();
                String teacherCode = SharedPrefManager.getInstance(c).getUser().getTeacherCode();

                String msg = "";
                if (holder.switchCompat.isChecked()) {
                    msg = "Deactivated";
                } else {
                    msg = "Activated";
                }

                boolean check = StatusUpdate(msg, orgCode, teacherCode);

                if (check == true && holder.switchCompat.isChecked()) {
                    holder.switchCompat.setChecked(false);
                } else if(check == true && !holder.switchCompat.isChecked()){
                    holder.switchCompat.setChecked(true);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public Boolean StatusUpdate(String msg, String orgCode, String teacherCode) {

        final Boolean[] ans = {false};

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage("Are you sure you want to " + msg)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ans[0] = true;
                        StatusChangeViewModel statusChangeViewModel = new StatusChangeViewModel();
                        statusChangeViewModel.statusChange(orgCode, teacherCode);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return ans[0];
    }
}

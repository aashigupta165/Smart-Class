package com.education.smartclass.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.holder.TeacherListHolder;
import com.education.smartclass.models.Teachers;
import com.education.smartclass.response.MessageResponse;
import com.education.smartclass.storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListHolder> {

    Context c;
    ArrayList<Teachers> teacherList;

    public TeacherListAdapter(Context c, ArrayList<Teachers> teacherList) {
        this.c = c;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_list_row, null);
        return new TeacherListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherListHolder holder, int position) {
        holder.teacherName.setText(teacherList.get(position).getTeacherName());
        holder.teacherCode.setText(teacherList.get(position).getTeacherCode());

        if (teacherList.get(position).getActive().equals("false")) {
            holder.status.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ic_check_box_outline));
        } else {
            holder.status.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ic_check_box));
        }

        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orgCode = SharedPrefManager.getInstance(c).getUser().getOrgCode();
                String teacherCode = teacherList.get(position).getTeacherCode();
                String msg = "";

                if (teacherList.get(position).getActive().equals("true")) {
                    msg = "Deactivate " + teacherList.get(position).getTeacherName();
                } else {
                    msg = "Activate " + teacherList.get(position).getTeacherName();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setMessage("Are you sure you want to " + msg)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Call<MessageResponse> call = RetrofitClient.getInstance().getApi().stateChange(orgCode, teacherCode);
                                call.enqueue(new Callback<MessageResponse>() {
                                    @Override
                                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                                        MessageResponse messageResponse = response.body();

                                        if (messageResponse.getMessage().equals("state_changed")) {
                                            if (teacherList.get(position).getActive().equals("true")) {
                                                holder.status.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ic_check_box_outline));
                                                teacherList.get(position).setActive("false");
                                            } else {
                                                holder.status.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ic_check_box));
                                                teacherList.get(position).setActive("true");
                                            }
                                        } else {
                                            Toast.makeText(c, "Try Again Later!", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                                        Toast.makeText(c, "Please Connect to the Internet!", Toast.LENGTH_LONG).show();
                                    }
                                });
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }
}

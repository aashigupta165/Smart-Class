package com.education.smartclass.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.ScheduleListHolder;
import com.education.smartclass.models.ReadScheduleDetails;
import com.education.smartclass.roles.Organisation.fragments.ManualTeacherRegisterFragment3;
import com.education.smartclass.roles.teacher.fragments.TeacherAddScheduleFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListHolder> {

    private Context c;
    private ArrayList<ReadScheduleDetails> readScheduleDetails;

    private ScheduleListHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(ScheduleListHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public ScheduleListAdapter(Context c, ArrayList<ReadScheduleDetails> readScheduleDetails) {
        this.c = c;
        this.readScheduleDetails = readScheduleDetails;
    }

    @NonNull
    @Override
    public ScheduleListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_schedule_list_row, null);
        return new ScheduleListHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleListHolder scheduleListHolder, int position) {
        if (readScheduleDetails.get(position).getTopicScheduled().equals("Subject")) {
            scheduleListHolder.subject.setText(readScheduleDetails.get(position).getSubjectScheduled());
        } else {
            scheduleListHolder.subject.setText("General Meeting");
        }
        scheduleListHolder.time.setText("Lecture: " + readScheduleDetails.get(position).getScheduleTime() + "(" + readScheduleDetails.get(position).getScheduleDate() + ")");
        scheduleListHolder.count.setText(readScheduleDetails.get(position).getStudentCount() + " Students");
        scheduleListHolder.standard.setText("STD: " + readScheduleDetails.get(position).getScheduledClass() + " " + readScheduleDetails.get(position).getScheduledSection());

//
//                            case R.id.edit:
//                                Bundle bundle = new Bundle();
//                                bundle.putString("class", readScheduleDetails.get(position).getScheduledClass());
//                                bundle.putString("section", readScheduleDetails.get(position).getScheduledSection());
//                                bundle.putString("subject", readScheduleDetails.get(position).getSubjectScheduled());
//                                bundle.putString("date", readScheduleDetails.get(position).getScheduleDate());
//                                bundle.putString("time", readScheduleDetails.get(position).getScheduleTime());
//                                TeacherAddScheduleFragment fragment = new TeacherAddScheduleFragment();
//                                fragment.setArguments(bundle);
//                                ((FragmentActivity) c).getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.nav_host_fragment, fragment)
//                                        .addToBackStack(null)
//                                        .commit();
//                                break;
//                            case R.id.delete:
//                                AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(c);
//                                builder.setMessage("Are you sure you want to Delete?")
//                                        .setCancelable(false)
//                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//
//                                            }
//                                        })
//                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.cancel();
//                                            }
//                                        });
//                                AlertDialog alertDialog = builder.create();
//                                alertDialog.show();
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return readScheduleDetails.size();
    }
}


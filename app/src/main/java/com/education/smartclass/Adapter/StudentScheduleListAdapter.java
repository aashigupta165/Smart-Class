package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.ScheduleListHolder;
import com.education.smartclass.models.ReadStudentScheduleDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentScheduleListAdapter extends RecyclerView.Adapter<ScheduleListHolder> implements Filterable {

    private Context c;
    private ArrayList<ReadStudentScheduleDetails> readStudentScheduleDetails;

    private ArrayList<ReadStudentScheduleDetails> filterList;

    private ScheduleListHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(ScheduleListHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public StudentScheduleListAdapter(Context c, ArrayList<ReadStudentScheduleDetails> readStudentScheduleDetails) {
        this.c = c;
        this.readStudentScheduleDetails = readStudentScheduleDetails;
        filterList = new ArrayList<>(readStudentScheduleDetails);
    }

    @NonNull
    @Override
    public ScheduleListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_schedule_list_row, null);
        return new ScheduleListHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleListHolder scheduleListHolder, int position) {
        if (readStudentScheduleDetails.get(position).getTopicScheduled().equals("Subject")) {
            scheduleListHolder.subject.setText(readStudentScheduleDetails.get(position).getSubjectScheduled());
        } else {
            scheduleListHolder.subject.setText("General Meeting");
        }
        scheduleListHolder.time.setText("Lecture: " + readStudentScheduleDetails.get(position).getScheduleTime() + "(" + readStudentScheduleDetails.get(position).getScheduleDate() + ")");
        scheduleListHolder.count.setText(readStudentScheduleDetails.get(position).getStudentCount() + " Students");
        scheduleListHolder.standard.setText(readStudentScheduleDetails.get(position).getTeacherName());
        scheduleListHolder.standard.setTextColor(c.getResources().getColor(R.color.colorPrimaryDark));
        scheduleListHolder.optionMenu.setVisibility(View.GONE);
        if (readStudentScheduleDetails.get(position).getScheduleDescription().equals("")) {
            scheduleListHolder.desc_card.setVisibility(View.GONE);
        } else {
            scheduleListHolder.description.setText(readStudentScheduleDetails.get(position).getScheduleDescription());
        }
    }

    @Override
    public int getItemCount() {
        return readStudentScheduleDetails.size();
    }

    @Override
    public Filter getFilter() {
        return filterOne;
    }

    private Filter filterOne = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ReadStudentScheduleDetails> filteredList = new ArrayList<>();

            if (constraint.equals("all")) {
                filteredList = filterList;
            } else if (constraint.equals("filter1")) {
                try {
                    SimpleDateFormat datequery = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    Date now = new Date();
                    datequery.format(now);
                    for (ReadStudentScheduleDetails readStudentScheduleDetails : filterList) {
                        Date date = datequery.parse(readStudentScheduleDetails.getScheduleDate() + " " + readStudentScheduleDetails.getScheduleTime());
                        if (now.before(date)) {
                            filteredList.add(readStudentScheduleDetails);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (constraint.equals("filter2")) {
                try {
                    SimpleDateFormat datequery = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    Date now = new Date();
                    datequery.format(now);
                    for (ReadStudentScheduleDetails readStudentScheduleDetails : filterList) {
                        Date date = datequery.parse(readStudentScheduleDetails.getScheduleDate() + " " + readStudentScheduleDetails.getScheduleTime());
                        if (now.after(date)) {
                            filteredList.add(readStudentScheduleDetails);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (constraint.equals("home_filter")) {
                try {
                    SimpleDateFormat datequery = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    Date now = new Date(System.currentTimeMillis() - 3600000);
                    datequery.format(now);
                    for (ReadStudentScheduleDetails readStudentScheduleDetails : filterList) {
                        Date date = datequery.parse(readStudentScheduleDetails.getScheduleDate() + " " + readStudentScheduleDetails.getScheduleTime());
                        if (now.before(date)) {
                            filteredList.add(readStudentScheduleDetails);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    SimpleDateFormat datequery = new SimpleDateFormat("dd-MM-yyyy");
                    Date now = datequery.parse(constraint.toString());
                    for (ReadStudentScheduleDetails readStudentScheduleDetails : filterList) {
                        Date date = datequery.parse(readStudentScheduleDetails.getScheduleDate());
                        if (now.compareTo(date) == 0) {
                            filteredList.add(readStudentScheduleDetails);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            readStudentScheduleDetails.clear();
            readStudentScheduleDetails.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

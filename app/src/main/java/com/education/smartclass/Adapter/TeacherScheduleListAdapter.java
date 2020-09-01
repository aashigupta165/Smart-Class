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
import com.education.smartclass.models.ReadTeacherScheduleDetails;
import com.education.smartclass.storage.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherScheduleListAdapter extends RecyclerView.Adapter<ScheduleListHolder> implements Filterable {

    private Context c;
    private ArrayList<ReadTeacherScheduleDetails> readTeacherScheduleDetails;

    private ArrayList<ReadTeacherScheduleDetails> filterList;

    private ScheduleListHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(ScheduleListHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public TeacherScheduleListAdapter(Context c, ArrayList<ReadTeacherScheduleDetails> readTeacherScheduleDetails) {
        this.c = c;
        this.readTeacherScheduleDetails = readTeacherScheduleDetails;
        filterList = new ArrayList<>(readTeacherScheduleDetails);
    }

    @NonNull
    @Override
    public ScheduleListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_schedule_list_row, null);
        return new ScheduleListHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleListHolder scheduleListHolder, int position) {
        if (readTeacherScheduleDetails.get(position).getTopicScheduled().equals("Subject")) {
            scheduleListHolder.subject.setText(readTeacherScheduleDetails.get(position).getSubjectScheduled());
        } else {
            scheduleListHolder.subject.setText("General Meeting");
        }
        scheduleListHolder.time.setText("Lecture: " + readTeacherScheduleDetails.get(position).getScheduleTime() + "(" + readTeacherScheduleDetails.get(position).getScheduleDate() + ")");
        scheduleListHolder.count.setText(readTeacherScheduleDetails.get(position).getStudentCount() + " Students");
        if (SharedPrefManager.getInstance(c).getUser().getRole().equals("Organisation")) {
            if (readTeacherScheduleDetails.get(position).getStudentALL().equals("true")) {
                scheduleListHolder.standard.setVisibility(View.GONE);
            } else {
                scheduleListHolder.standard.setText("STD: " + readTeacherScheduleDetails.get(position).getScheduledClass() + " " +
                        readTeacherScheduleDetails.get(position).getScheduledSection());
            }
        } else {
            scheduleListHolder.standard.setText("STD: " + readTeacherScheduleDetails.get(position).getScheduledClass() + " " +
                    readTeacherScheduleDetails.get(position).getScheduledSection());
        }
        if (readTeacherScheduleDetails.get(position).getScheduleDescription().equals("")) {
            scheduleListHolder.desc_card.setVisibility(View.GONE);
        } else {
            scheduleListHolder.description.setText(readTeacherScheduleDetails.get(position).getScheduleDescription());
        }
    }

    @Override
    public int getItemCount() {
        return readTeacherScheduleDetails.size();
    }

    @Override
    public Filter getFilter() {
        return filterOne;
    }

    private Filter filterOne = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ReadTeacherScheduleDetails> filteredList = new ArrayList<>();

            if (constraint.equals("all")) {
                filteredList = filterList;
            } else if (constraint.equals("filter1")) {
                try {
                    SimpleDateFormat datequery = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    Date now = new Date();
                    datequery.format(now);
                    for (ReadTeacherScheduleDetails readTeacherScheduleDetails : filterList) {
                        Date date = datequery.parse(readTeacherScheduleDetails.getScheduleDate() + " " + readTeacherScheduleDetails.getScheduleTime());
                        if (now.before(date)) {
                            filteredList.add(readTeacherScheduleDetails);
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
                    for (ReadTeacherScheduleDetails readTeacherScheduleDetails : filterList) {
                        Date date = datequery.parse(readTeacherScheduleDetails.getScheduleDate() + " " + readTeacherScheduleDetails.getScheduleTime());
                        if (now.after(date)) {
                            filteredList.add(readTeacherScheduleDetails);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    SimpleDateFormat datequery = new SimpleDateFormat("dd-MM-yyyy");
                    Date now = datequery.parse(constraint.toString());
                    for (ReadTeacherScheduleDetails readTeacherScheduleDetails : filterList) {
                        Date date = datequery.parse(readTeacherScheduleDetails.getScheduleDate());
                        if (now.compareTo(date) == 0) {
                            filteredList.add(readTeacherScheduleDetails);
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
            readTeacherScheduleDetails.clear();
            readTeacherScheduleDetails.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}


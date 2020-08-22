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
import com.education.smartclass.holder.QuestionListHolder;
import com.education.smartclass.models.Question;
import com.education.smartclass.models.ReadTeacherScheduleDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListHolder> implements Filterable {

    private Context c;
    private ArrayList<Question> questions;

    private ArrayList<Question> filterList;

    private QuestionListHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(QuestionListHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public QuestionListAdapter(Context c, ArrayList<Question> questions) {
        this.c = c;
        this.questions = questions;
        filterList = new ArrayList<>(questions);
    }

    @NonNull
    @Override
    public QuestionListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_row, null);
        return new QuestionListHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListHolder questionListHolder, int position) {
        if (questions.get(position).getPurposeOfQuestion().equals("Subject")) {
            questionListHolder.subject.setText(questions.get(position).getSubject());
        } else {
            questionListHolder.subject.setText("General Question");
        }
        if (questions.get(position).getQuestionAskerRole().equals("Student")) {
            questionListHolder.standard.setText("STD: " + questions.get(position).getQuestionForClass() + " " + questions.get(position).getQuestionForSection());
        } else {
            questionListHolder.standard.setText("Faculty");
        }
        questionListHolder.time.setText(questions.get(position).getQuestionDateTime());
        questionListHolder.name.setText("Posted by: " + questions.get(position).getQuestionAskerName());
        questionListHolder.question.setText(questions.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @Override
    public Filter getFilter() {
        return filterOne;
    }

    private Filter filterOne = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Question> filteredList = new ArrayList<>();

            if (constraint.equals("all")) {
                filteredList = filterList;
            } else if (constraint.equals("filter1")) {
                for (Question question : filterList) {
                    if (question.getQuestionAskerRole().equals("Teacher")) {
                        filteredList.add(question);
                    }
                }
            } else if (constraint.equals("filter2")) {
                for (Question question : filterList) {
                    if (question.getQuestionAskerRole().equals("Student")) {
                        filteredList.add(question);
                    }
                }
            } else if (constraint.equals("filter3")){
                for (Question question : filterList) {
                    if (question.getPurposeOfQuestion().equals("Subject")) {
                        filteredList.add(question);
                    }
                }
            } else if (constraint.equals("filter4")){
                for (Question question : filterList) {
                    if (question.getPurposeOfQuestion().equals("Other")) {
                        filteredList.add(question);
                    }
                }
            } else {
                try {
                    SimpleDateFormat datequery = new SimpleDateFormat("dd-MM-yyyy");
                    Date now = datequery.parse(constraint.toString());
                    for (Question question : filterList) {
                        Date date = datequery.parse(question.getQuestionDateTime());
                        if (now.compareTo(date) == 0) {
                            filteredList.add(question);
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
            questions.clear();
            questions.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

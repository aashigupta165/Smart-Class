package com.education.smartclass.roles.teacher.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.QuestionListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.holder.QuestionListHolder;
import com.education.smartclass.models.Question;
import com.education.smartclass.roles.teacher.model.FetchQuestionViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;
import java.util.Calendar;

public class TeacherQuestionaireFragment extends Fragment {

    private ImageView filter;
    private TextView no_data;
    private RelativeLayout relativeLayout;
    private RecyclerView question_list;

    private FetchQuestionViewModel fetchQuestionViewModel;

    private ArrayList<Question> questionArrayList;

    private QuestionListAdapter questionListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_questionaire, container, false);

        filter = view.findViewById(R.id.filter);
        no_data = view.findViewById(R.id.no_class);
        question_list = view.findViewById(R.id.question_list);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        dataObserver();
        buttonClickEvents();

        fetchQuestionViewModel.fetchQuestions(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode());

        return view;
    }

    private void buttonClickEvents() {

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterSelectionOptions(v);
            }
        });
    }

    private void filterSelectionOptions(View v) {

        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Menu menu = popupMenu.getMenu();
                menu.findItem(R.id.coming).setVisible(false);
                menu.findItem(R.id.previous).setVisible(false);
                menu.findItem(R.id.by_teacher).setVisible(true);
                menu.findItem(R.id.by_student).setVisible(true);
                menu.findItem(R.id.by_subject).setVisible(true);
                menu.findItem(R.id.other).setVisible(true);

                switch (item.getItemId()) {
                    case R.id.all:
                        questionListAdapter.getFilter().filter("all");
                        return true;
                    case R.id.by_teacher:
                        questionListAdapter.getFilter().filter("filter1");
                        return true;
                    case R.id.by_student:
                        questionListAdapter.getFilter().filter("filter2");
                        return true;
                    case R.id.by_subject:
                        questionListAdapter.getFilter().filter("filter3");
                        return true;
                    case R.id.other:
                        questionListAdapter.getFilter().filter("filter4");
                        return  true;
                    case R.id.by_date:
                        selectDate();
                        return true;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void selectDate() {

        DatePickerDialog.OnDateSetListener setListener;
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String selectedDate = dayOfMonth + "-" + month + "-" + year;
                questionListAdapter.getFilter().filter(selectedDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    private void dataObserver() {

        fetchQuestionViewModel = ViewModelProviders.of(this).get(FetchQuestionViewModel.class);
        LiveData<String> message = fetchQuestionViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "list_found":
                        fetchList();
                        break;
                    case "invalid_orgCode":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Invalid Credentials");
                }
            }
        });
    }

    private void fetchList() {

        LiveData<ArrayList<Question>> list = fetchQuestionViewModel.getList();

        list.observe(this, new Observer<ArrayList<Question>>() {
            @Override
            public void onChanged(ArrayList<Question> questions) {

                questionArrayList = questions;

                question_list.setLayoutManager(new LinearLayoutManager(getContext()));
                questionListAdapter = new QuestionListAdapter(getContext(), questions);
                question_list.setAdapter(questionListAdapter);

                questionListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if (questionListAdapter.getItemCount() == 0) {
                            no_data.setVisibility(View.VISIBLE);
                        }
                    }
                });

                questionListAdapter.setOnItemClickListener(new QuestionListHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        openQuestionDetails(position);
                    }
                });
            }
        });
    }

    private void openQuestionDetails(int position) {

        Bundle bundle = new Bundle();

        bundle.putString("questionId", questionArrayList.get(position).getQuestionId());
        bundle.putString("question", questionArrayList.get(position).getQuestion());
        bundle.putString("questionType", questionArrayList.get(position).getPurposeOfQuestion());
        bundle.putString("askerName", questionArrayList.get(position).getQuestionAskerName());
        bundle.putString("askerRole", questionArrayList.get(position).getQuestionAskerRole());
        bundle.putString("askerClass", questionArrayList.get(position).getQuestionForClass());
        bundle.putString("askerSection", questionArrayList.get(position).getQuestionForSection());
        bundle.putString("date", questionArrayList.get(position).getQuestionDateTime());

        TeacherQuestionRepliesFragment fragment = new TeacherQuestionRepliesFragment();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }
}
package com.education.smartclass.roles.teacher.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.education.smartclass.R;
import com.education.smartclass.models.StudentDetail;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.models.TeacherSubjects;
import com.education.smartclass.roles.teacher.model.FetchDropdownDetailsViewModel;
import com.education.smartclass.roles.teacher.model.FetchStudentListViewModel;
import com.education.smartclass.roles.teacher.model.ScheduleAddViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;
import java.util.Calendar;

public class TeacherAddScheduleFragment extends Fragment {

    private AutoCompleteTextView subject, className, section;
    private ImageView subjectDropdown, classDropdown, sectionDropdown;
    private TextView date, time, submitbtn;
    private RadioGroup student_selection;
    private RadioButton radioButton, all, selected;

    private RelativeLayout relativeLayout;

    private ProgressDialog progressDialog;

    private ScheduleAddViewModel scheduleAddViewModel;
    private FetchDropdownDetailsViewModel fetchDropdownDetailsViewModel;
    private FetchStudentListViewModel fetchStudentListViewModel;

    private ArrayList<TeacherClasses> teacherClassesArrayList = new ArrayList<TeacherClasses>();
    private ArrayList<String> studentDetailArrayList = new ArrayList<>();

    private ArrayList<Integer> studentItems = new ArrayList<>();
    private boolean[] checkedItems;

    private DatePickerDialog.OnDateSetListener setListener;
    private Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);
    private final int hour = calendar.get(Calendar.HOUR_OF_DAY);
    private final int minute = calendar.get(Calendar.MINUTE);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_add_schedule, container, false);

        subject = view.findViewById(R.id.subject);
        className = view.findViewById(R.id.student_Class);
        section = view.findViewById(R.id.student_Section);
        subjectDropdown = view.findViewById(R.id.subject_drop_down);
        classDropdown = view.findViewById(R.id.class_drop_down);
        sectionDropdown = view.findViewById(R.id.section_drop_down);
        student_selection = view.findViewById(R.id.student_selection);
        all = view.findViewById(R.id.all);
        selected = view.findViewById(R.id.selected);
        submitbtn = view.findViewById(R.id.submitbtn);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressDialog = new ProgressDialog(getContext());

        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);

        dataObserver();
        fetchData();
        TextSelector(view);
        buttonClickEvents();

        return view;
    }

    private void TextSelector(View view) {

        classDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                className.showDropDown();
                section.setText("");
                subject.setText("");
            }
        });

        sectionDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (className.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Select Class!");
                    return;
                } else {
                    ArrayList<String> sections = new ArrayList<>();
                    int i = 0;
                    for (TeacherClasses s : teacherClassesArrayList) {
                        if (s.getTeacherClass().equals(className.getText().toString())) {
                            sections.add(i, s.getTeacherSection());
                            i++;
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, sections);
                    section.setAdapter(adapter);
                    section.showDropDown();
                    subject.setText("");
                }
            }
        });

        subjectDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (className.getText().toString().equals("") || section.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Select Above Fields!");
                    return;
                } else {
                    ArrayList<String> subjects = new ArrayList<>();
                    int i = 0;
                    for (TeacherClasses s : teacherClassesArrayList) {
                        if (s.getTeacherClass().equals(className.getText().toString()) && s.getTeacherSection().equals(section.getText().toString())) {
                            for (TeacherSubjects t : s.getTeachingSubjects())
                                subjects.add(i, t.getSubject());
                            i++;
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, subjects);
                    subject.setAdapter(adapter);
                    subject.showDropDown();
                }
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "am";
                        } else {
                            AM_PM = "pm";
                            if (hourOfDay != 12) {
                                hourOfDay %= 12;
                            }
                        }
                        time.setText(hourOfDay + ":" + minute + AM_PM);
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String selectedDate = day + "-" + month + "-" + year;
                date.setText(selectedDate);
            }
        };

        student_selection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(checkedId);
                if (radioButton.getText().toString().equals("All Students")) {

                } else {
                    if (className.getText().toString().equals("") || section.getText().toString().equals("")) {
                        new SnackBar(relativeLayout, "Please Fill Above Details First!");
                        all.setChecked(true);
                        return;
                    } else {
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
                        fetchStudentListViewModel.fetchStudents(orgCode, className.getText().toString(), section.getText().toString());
                    }
                }
            }
        });
    }

    private void buttonClickEvents() {

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (className.getText().toString().equals("") || section.getText().toString().equals("") || subject.getText().toString().equals("") ||
                        date.getText().toString().equals("") || time.getText().toString().equals("") || studentDetailArrayList.size() == 0) {
                    new SnackBar(relativeLayout, "Please Enter All Details!");
                    return;
                }

                if (radioButton.getText().toString().equals("All Students")) {
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
                    fetchStudentListViewModel.fetchStudents(orgCode, className.getText().toString(), section.getText().toString());
                } else {
                    String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
                    String teacherCode = SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode();
                    scheduleAddViewModel.scheduleCreate(orgCode, teacherCode, className.getText().toString(), section.getText().toString(), subject.getText().toString(),
                            date.getText().toString(), time.getText().toString(), studentDetailArrayList);
                }
            }
        });
    }

    private void dataObserver() {

        fetchDropdownDetailsViewModel = ViewModelProviders.of(this).get(FetchDropdownDetailsViewModel.class);
        LiveData<String> message = fetchDropdownDetailsViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                switch (s) {
                    case "teacher_detail_found":
                        setDropdown();
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please try again later!");
                }
            }
        });

        fetchStudentListViewModel = ViewModelProviders.of(this).get(FetchStudentListViewModel.class);
        LiveData<String> msg = fetchStudentListViewModel.getMessage();

        msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "list_found":
                        setStudentList();
                        break;
                    case "Internet_Issue":
                        progressDialog.dismiss();
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        progressDialog.dismiss();
                        new SnackBar(relativeLayout, "Invalid Credentials");
                }
            }
        });

        scheduleAddViewModel = ViewModelProviders.of(this).get(ScheduleAddViewModel.class);
        LiveData<String> msgs = scheduleAddViewModel.getMessage();

        msgs.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "class_scheduled":
                        progressDialog.dismiss();
                        TeacherAddScheduleFragment fragment = new TeacherAddScheduleFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        new SnackBar(relativeLayout, "Class Scheduled");
                        break;
                    case "class_not_scheduled":
                        progressDialog.dismiss();
                        new SnackBar(relativeLayout, "Class cannot be Scheduled.");
                        break;
                    case "Internet_Issue":
                        progressDialog.dismiss();
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        progressDialog.dismiss();
                        new SnackBar(relativeLayout, "Invalid Credentials");
                }
            }
        });
    }

    private void fetchData() {

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
        String teacherCode = SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode();

        fetchDropdownDetailsViewModel.fetchDropdownDetails(orgCode, teacherCode);
    }

    private void setDropdown() {

        LiveData<ArrayList<TeacherClasses>> list = fetchDropdownDetailsViewModel.getList();

        list.observe(getViewLifecycleOwner(), new Observer<ArrayList<TeacherClasses>>() {
            @Override
            public void onChanged(ArrayList<TeacherClasses> teacherClass) {
                teacherClassesArrayList = teacherClass;
                ArrayList<String> classes = new ArrayList<>();
                int i = 0;
                for (TeacherClasses s : teacherClass) {
                    classes.add(i, s.getTeacherClass());
                    i++;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, classes);
                className.setAdapter(adapter);
            }
        });
    }

    private void setStudentList() {

        LiveData<ArrayList<StudentDetail>> list = fetchStudentListViewModel.getList();

        if (radioButton.getText().toString().equals("All Students")) {
            list.observe(getViewLifecycleOwner(), new Observer<ArrayList<StudentDetail>>() {
                @Override
                public void onChanged(ArrayList<StudentDetail> studentList) {
                    studentDetailArrayList.clear();
                    int i = 0;
                    for (StudentDetail s : studentList) {
                        studentDetailArrayList.add(i, s.getStudentName() + "_" + s.getStudentRollNo() + "_" + s.getStudentEmail());
                        i++;
                    }
                    createSchedule();
                }
            });
        } else {
            list.observe(getViewLifecycleOwner(), new Observer<ArrayList<StudentDetail>>() {
                @Override
                public void onChanged(ArrayList<StudentDetail> studentList) {

                    checkedItems = new boolean[studentList.size()];

                    String[] students = new String[studentList.size()];
                    String[] name = new String[studentList.size()];
                    String[] rollno = new String[studentList.size()];
                    String[] email = new String[studentList.size()];

                    int i = 0;
                    for (StudentDetail s : studentList) {
                        students[i] = s.getStudentName() + " (" + s.getStudentRollNo() + ")";
                        name[i] = s.getStudentName();
                        rollno[i] = s.getStudentRollNo();
                        email[i] = s.getStudentEmail();
                        i++;
                    }
                    showStudentList(students, name, rollno, email);
                }
            });
        }
    }

    private void showStudentList(String[] students, String[] name, String[] rollno, String[] email) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Students");
        builder.setMultiChoiceItems(students, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked) {
                    if (!studentItems.contains(position)) {
                        studentItems.add(position);
                    }
                } else if (studentItems.contains(position)) {
                    studentItems.remove(position);
                }
            }
        });

        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                studentDetailArrayList.clear();
                for (int i = 0; i < studentItems.size(); i++) {
                    studentDetailArrayList.add(i, name[studentItems.get(i)] + "_" + rollno[studentItems.get(i)] + "_" + email[studentItems.get(i)]);
                }
            }
        });

        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    studentItems.clear();
                }
            }
        });

        progressDialog.dismiss();

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void createSchedule() {

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
        String teacherCode = SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode();

        scheduleAddViewModel.scheduleCreate(orgCode, teacherCode, className.getText().toString(), section.getText().toString(), subject.getText().toString(),
                date.getText().toString(), time.getText().toString(), studentDetailArrayList);
    }
}
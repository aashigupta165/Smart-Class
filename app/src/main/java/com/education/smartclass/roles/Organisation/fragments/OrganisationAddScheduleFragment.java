package com.education.smartclass.roles.Organisation.fragments;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.education.smartclass.R;
import com.education.smartclass.models.OrgClassList;
import com.education.smartclass.models.StudentDetail;
import com.education.smartclass.roles.Organisation.model.ClassListViewModel;
import com.education.smartclass.roles.Organisation.model.ScheduleCreateViewModel;
import com.education.smartclass.roles.teacher.model.FetchStudentListViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class OrganisationAddScheduleFragment extends Fragment {

    private AutoCompleteTextView className, section;
    private ImageView classDropdown, sectionDropdown;
    private TextView date, time, select_students, submitbtn;
    private EditText description;
    private RadioGroup attandees;
    private RadioButton radioButton;

    private RelativeLayout relativeLayout;

    private ProgressDialog progressDialog;

    private ClassListViewModel classListViewModel;
    private FetchStudentListViewModel fetchStudentListViewModel;
    private ScheduleCreateViewModel scheduleCreateViewModel;

    private ArrayList<String> studentDetailArrayList = new ArrayList<>();
    private ArrayList<OrgClassList> orgClassLists;

    private ArrayList<Integer> studentItems = new ArrayList<>();
    private boolean[] checkedItems;
    private String[] students, name, rollno, email;

    private DatePickerDialog.OnDateSetListener setListener;
    private Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);
    private final int hour = calendar.get(Calendar.HOUR_OF_DAY);
    private final int minute = calendar.get(Calendar.MINUTE);

    private String radioSelected = "Selected";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organisation_add_schedule, container, false);

        attandees = view.findViewById(R.id.selection);
        className = view.findViewById(R.id.student_Class);
        section = view.findViewById(R.id.student_Section);
        classDropdown = view.findViewById(R.id.class_drop_down);
        sectionDropdown = view.findViewById(R.id.section_drop_down);
        select_students = view.findViewById(R.id.student_selection);
        select_students.setVisibility(View.GONE);
        description = view.findViewById(R.id.description);
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

        attandees.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(checkedId);

                if (radioButton.getText().toString().equals("All")) {
                    radioSelected = "All";
                    className.setVisibility(View.GONE);
                    section.setVisibility(View.GONE);
                    classDropdown.setVisibility(View.GONE);
                    sectionDropdown.setVisibility(View.GONE);
                    select_students.setVisibility(View.GONE);
                    className.setText("");
                    section.setText("");
                } else {
                    radioSelected = "Selected";
                    fetchData();
                    className.setVisibility(View.VISIBLE);
                    section.setVisibility(View.VISIBLE);
                    classDropdown.setVisibility(View.VISIBLE);
                    sectionDropdown.setVisibility(View.VISIBLE);
                }
            }
        });


        classDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                className.showDropDown();
                section.setText("");
                select_students.setText("");
                select_students.setHint("Select Students");
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
                    for (OrgClassList s : orgClassLists) {
                        if (s.getOrgClass().equals(className.getText().toString())) {
                            sections.add(i, s.getOrgSection());
                            i++;
                        }
                    }
                    sections = new ArrayList<String>(new HashSet<String>(sections));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, sections);
                    section.setAdapter(adapter);
                    section.showDropDown();
                    select_students.setText("");
                    select_students.setHint("Select Students");
                }
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioSelected.equals("Selected")) {
                    if (className.getText().toString().equals("") || section.getText().toString().equals("")) {
                        new SnackBar(relativeLayout, "Please Fill Above details");
                        return;
                    }
                    String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

                    fetchStudentListViewModel.fetchStudents(orgCode, className.getText().toString(), section.getText().toString());
                }

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
                String selectedDate = dayOfMonth + "-" + month + "-" + year;
                date.setText(selectedDate);
            }
        };

        select_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (className.getText().toString().equals("") || section.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Fill Above Details!");
                    return;
                }
                showStudentList();
            }
        });
    }

    private void buttonClickEvents() {

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSchedule();
            }
        });
    }

    private void dataObserver() {

        classListViewModel = ViewModelProviders.of(this).get(ClassListViewModel.class);
        LiveData<String> message = classListViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "list_found":
                        setDropdown();
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
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
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "There is an error in fetching Details... Please Try Again Later!");
                }
            }
        });

        scheduleCreateViewModel = ViewModelProviders.of(this).get(ScheduleCreateViewModel.class);
        LiveData<String> msgs = scheduleCreateViewModel.getMessage();

        msgs.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "class_scheduled":
                        OrganisationScheduleFragment fragment = new OrganisationScheduleFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        new SnackBar(relativeLayout, "Class Scheduled");
                        break;
                    case "class_not_scheduled":
                        new SnackBar(relativeLayout, "Class cannot be Scheduled.");
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

    private void fetchData() {

        progressDialog.setMessage("Data Searching...");
        progressDialog.show();

        if (radioSelected.equals("Selected")) {
            classListViewModel.fetchStudentList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), "Class");
        }
    }

    private void setDropdown() {

        LiveData<ArrayList<OrgClassList>> list = classListViewModel.getList();

        list.observe(this, new Observer<ArrayList<OrgClassList>>() {
            @Override
            public void onChanged(ArrayList<OrgClassList> classLists) {

                orgClassLists = classLists;

                ArrayList<String> classes = new ArrayList<>();
                int i = 0;
                for (OrgClassList s : orgClassLists) {
                    classes.add(i, s.getOrgClass());
                    i++;
                }
                classes = new ArrayList<String>(new HashSet<String>(classes));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, classes);
                className.setAdapter(adapter);
            }
        });
    }

    private void setStudentList() {

        LiveData<ArrayList<StudentDetail>> list = fetchStudentListViewModel.getList();

        list.observe(getViewLifecycleOwner(), new Observer<ArrayList<StudentDetail>>() {
            @Override
            public void onChanged(ArrayList<StudentDetail> studentList) {

                select_students.setVisibility(View.VISIBLE);

                checkedItems = new boolean[studentList.size()];

                students = new String[studentList.size()];
                name = new String[studentList.size()];
                rollno = new String[studentList.size()];
                email = new String[studentList.size()];

                int i = 0;
                for (StudentDetail s : studentList) {
                    students[i] = s.getStudentName() + " (" + s.getStudentRollNo() + ")";
                    name[i] = s.getStudentName();
                    rollno[i] = s.getStudentRollNo();
                    email[i] = s.getStudentEmail();
                    i++;
                }
            }
        });
    }

    private void showStudentList() {

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
                select_students.setText(studentItems.size() + " Students");
            }
        });

        builder.setNegativeButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    studentDetailArrayList.clear();
                    studentItems.clear();
                    select_students.setText("");
                    select_students.setHint("Select Students");
                }
            }
        });

        builder.setNeutralButton("Select All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                studentDetailArrayList.clear();
                for (int i = 0; i < students.length; i++) {
                    studentItems.add(i);
                    studentDetailArrayList.add(i, name[i] + "_" + rollno[i] + "_" + email[i]);
                    checkedItems[i] = true;
                }
                select_students.setText(studentItems.size() + " Students");
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void createSchedule() {

        if (radioSelected.equals("Selected")) {
            if (className.getText().toString().equals("") || section.getText().toString().equals("") || studentDetailArrayList.size() == 0) {
                new SnackBar(relativeLayout, "Please Enter All Details!");
                return;
            }
        }

        if (date.getText().toString().equals("") || time.getText().toString().equals("")) {
            new SnackBar(relativeLayout, "Please Enter All Details!");
            return;
        }

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

        String ans = "0";

        if (radioSelected.equals("All")) {
            ans = "1";
        }

        scheduleCreateViewModel.scheduleCreate(orgCode, "Organisation", className.getText().toString(), section.getText().toString(), "Other",
                ans, date.getText().toString(), time.getText().toString(), description.getText().toString(), studentDetailArrayList);
    }
}
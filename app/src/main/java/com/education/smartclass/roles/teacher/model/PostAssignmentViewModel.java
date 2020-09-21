package com.education.smartclass.roles.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAssignmentViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void postAssignment(String orgCode, String teacherCode, String assignmentTitle, String description, String classAssignment, String sectionAssignment,
                               String subjectAssignment, String assignmentType, MultipartBody.Part file) {

        String assignmentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String assignmentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        RequestBody OrgCode = RequestBody.create(orgCode, MediaType.parse("text/plain"));
        RequestBody TeacherCode = RequestBody.create(teacherCode, MediaType.parse("text/plain"));
        RequestBody AssignmentTitle = RequestBody.create(assignmentTitle, MediaType.parse("text/plain"));
        RequestBody Description = RequestBody.create(description, MediaType.parse("text/plain"));
        RequestBody ClassAssignment = RequestBody.create(classAssignment, MediaType.parse("text/plain"));
        RequestBody SectionAssignment = RequestBody.create(sectionAssignment, MediaType.parse("text/plain"));
        RequestBody SubjectAssignment = RequestBody.create(subjectAssignment, MediaType.parse("text/plain"));
        RequestBody AssignmentDate = RequestBody.create(assignmentDate, MediaType.parse("text/plain"));
        RequestBody AssignmentTime = RequestBody.create(assignmentTime, MediaType.parse("text/plain"));
        RequestBody Role = RequestBody.create("Assignment", MediaType.parse("text/plain"));
        RequestBody AssignmentType = RequestBody.create(assignmentType, MediaType.parse("text/plain"));

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().postAssignment(OrgCode, TeacherCode, AssignmentTitle, Description, ClassAssignment,
                SectionAssignment, SubjectAssignment, AssignmentDate, AssignmentTime, Role, AssignmentType, file);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse messageResponse = response.body();
                    message.setValue(messageResponse.getMessage());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                message.setValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}

package com.education.smartclass.api;

import com.education.smartclass.models.StudentDetail;
import com.education.smartclass.response.DropdownDetails;
import com.education.smartclass.response.LoginResponse;
import com.education.smartclass.response.MessageResponse;
import com.education.smartclass.response.OrganisationList;
import com.education.smartclass.response.StudentList;
import com.education.smartclass.response.TeacherList;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("mobile") String mobile,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("showList")
    Call<OrganisationList> showList(
            @Field("currentRole") String currentRole,
            @Field("orgCode") String orgCode
    );

    @Multipart
    @POST("register")
    Call<MessageResponse> registerOrg(
            @Part("orgName") RequestBody orgName,
            @Part("orgCode") RequestBody orgCode,
            @Part("orgType") RequestBody orgType,
            @Part("orgAddress") RequestBody orgAddress,
            @Part("orgEmail") RequestBody orgEmail,
            @Part("role") RequestBody role,
            @Part("mobile") RequestBody mobile,
            @Part("methodToCreate") RequestBody methodToCreate,
            @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST("register")
    Call<MessageResponse> registerTeacher(
            @Field("teacherName") String teacherName,
            @Field("teacherAge") String teacherAge,
            @Field("teacherDesignation") String teacherDesignation,
            @Field("teacherCode") String teacherCode,
            @Field("teacherGender") String teacherGender,
            @Field("role") String role,
            @Field("teacherEmail") String teacherEmail,
            @Field("mobile") String mobile,
            @Field("class_section_subject") ArrayList<String> class_section_subject,
            @Field("orgCode") String orgCode,
            @Field("methodToCreate") String methodToCreate
    );

    @FormUrlEncoded
    @POST("register")
    Call<MessageResponse> registerStudent(
            @Field("studentName") String studentName,
            @Field("studentRollNo") String studentRollNo,
            @Field("studentClass") String studentClass,
            @Field("studentSection") String studentSection,
            @Field("studentFatherName") String studentFatherName,
            @Field("role") String role,
            @Field("studentEmail") String studentEmail,
            @Field("mobile") String mobile,
            @Field("studentDOB") String studentDOB,
            @Field("studentGender") String studentGender,
            @Field("orgCode") String orgCode,
            @Field("methodToCreate") String methodToCreate
    );

    @Multipart
    @POST("register")
    Call<MessageResponse> registerTeachers(
            @Part("role") RequestBody role,
            @Part("orgCode") RequestBody orgCode,
            @Part("methodToCreate") RequestBody methodToCreate,
            @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST("register")
    Call<MessageResponse> registerClass(
            @Field("role") String role,
            @Field("orgCode") String orgCode,
            @Field("class_section_subject") ArrayList<String> class_section_subject,
            @Field("methodToCreate") String methodToCreate
    );

    @FormUrlEncoded
    @POST("showList")
    Call<TeacherList> showTeacherList(
            @Field("currentRole") String currentRole,
            @Field("orgCode") String orgCode
    );

    @FormUrlEncoded
    @POST("changeState")
    Call<MessageResponse> stateChange(
            @Field("orgCode") String orgCode,
            @Field("teacherCode") String teacherCode
    );

    @FormUrlEncoded
    @POST("teacher/details")
    Call<DropdownDetails> fetchDropdown(
            @Field("orgCode") String orgCode,
            @Field("teacherCode") String teacherCode
    );

    @FormUrlEncoded
    @POST("showList")
    Call<StudentList> fetchStudents(
            @Field("currentRole") String currentRole,
            @Field("orgCode") String orgCode,
            @Field("studentClass") String studentClass,
            @Field("studentSection") String studentSection
    );

    @FormUrlEncoded
    @POST("schedule/create")
    Call<MessageResponse> createSchedule(
            @Field("orgCode") String ordCode,
            @Field("teacherCode") String teacherCode,
            @Field("classScheduled") String classScheduled,
            @Field("sectionScheduled") String sectionScheduled,
            @Field("topicScheduled") String topicScheduled,
            @Field("subjectScheduled") String subjectScheduled,
            @Field("scheduleDate") String scheduleDate,
            @Field("scheduleTime") String scheduleTime,
            @Field("selectedStudents") ArrayList<String> selectedStudents
    );
}
package com.education.smartclass.api;

import com.education.smartclass.models.ClassCSVDataBody;
import com.education.smartclass.models.OrgClassList;
import com.education.smartclass.models.StudentCSVDataBody;
import com.education.smartclass.models.TeacherCSVDataBody;
import com.education.smartclass.response.ClassList;
import com.education.smartclass.response.DropdownDetails;
import com.education.smartclass.response.FetchQuestionList;
import com.education.smartclass.response.FetchRepliesOfAQuestion;
import com.education.smartclass.response.FetchSubjectList;
import com.education.smartclass.response.LoginResponse;
import com.education.smartclass.response.MessageResponse;
import com.education.smartclass.response.OrganisationList;
import com.education.smartclass.response.StudentScheduleResponse;
import com.education.smartclass.response.TeacherScheduleResponse;
import com.education.smartclass.response.StudentList;
import com.education.smartclass.response.TeacherList;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
            @Field("password") String password,
            @Field("deviceToken") String deviceToken
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

    @POST("register")
    Call<MessageResponse> registerTeachers(
            @Body TeacherCSVDataBody teacherCSVDataBody
    );

    @POST("register")
    Call<MessageResponse> registerClasses(
            @Body ClassCSVDataBody classCSVDataBody
    );

    @POST("register")
    Call<MessageResponse> registerStudents(
            @Body StudentCSVDataBody studentCSVDataBody
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
            @Field("description") String description,
            @Field("selectedStudents") ArrayList<String> selectedStudents
    );

    @FormUrlEncoded
    @POST("schedule/read")
    Call<TeacherScheduleResponse> readTeacherSchedule(
            @Field("orgCode") String orgCode,
            @Field("role") String role,
            @Field("teacherCode") String teacherCode
    );

    @FormUrlEncoded
    @POST("schedule/update")
    Call<MessageResponse> updateSchedule(
            @Field("orgCode") String orgCode,
            @Field("teacherCode") String teacherCode,
            @Field("scheduleId") String scheduleId,
            @Field("newScheduleDate") String newScheduleDate,
            @Field("newScheduleTime") String newScheduleTime
    );

    @FormUrlEncoded
    @POST("schedule/delete")
    Call<MessageResponse> deleteSchedule(
            @Field("orgCode") String orgCode,
            @Field("teacherCode") String teacherCode,
            @Field("scheduleId") String scheduleId
    );

    @FormUrlEncoded
    @POST("schedule/read")
    Call<StudentScheduleResponse> readStudentSchedule(
            @Field("orgCode") String orgCode,
            @Field("role") String role,
            @Field("studentClass") String studentClass,
            @Field("studentSection") String studentSection,
            @Field("studentRollNo") String studentRollNo
    );

    @FormUrlEncoded
    @POST("student/subject")
    Call<FetchSubjectList> fetchSubjects(
            @Field("orgCode") String orgCode,
            @Field("studentClass") String studentClass,
            @Field("studentSection") String studentSection
    );

    @FormUrlEncoded
    @POST("question/ask")
    Call<MessageResponse> teacherPostQuestion(
            @Field("orgCode") String orgCode,
            @Field("purposeOfQuestion") String purposeOfQuestion,
            @Field("subject") String subject,
            @Field("question") String question,
            @Field("questionAskerName") String questionAskerName,
            @Field("questionAskerCode") String questionAskerCode,
            @Field("questionAskerRole") String questionAskerRole,
            @Field("questionForClass") String questionForClass,
            @Field("questionForSection") String questionForSection,
            @Field("questionDateTime") String questionDateTime
    );

    @FormUrlEncoded
    @POST("question/reply/create")
    Call<MessageResponse> replyToQuestion(
            @Field("orgCode") String orgCode,
            @Field("questionId") String questionId,
            @Field("reply") String reply,
            @Field("replierName") String replierName,
            @Field("replierRole") String replierRole,
            @Field("replierCode") String replierCode,
            @Field("replierClass") String replierClass,
            @Field("replierSection") String replierSection,
            @Field("replyDateTime") String replyDateTime
    );

    @FormUrlEncoded
    @POST("question/delete")
    Call<MessageResponse> deleteQuestion(
            @Field("orgCode") String orgCode,
            @Field("questionId") String questionId
    );

    @FormUrlEncoded
    @POST("question/reply/delete")
    Call<MessageResponse> deleteReply(
            @Field("orgCode") String orgCode,
            @Field("questionId") String questionId,
            @Field("replyId") String replyId
    );

    @FormUrlEncoded
    @POST("user/sendOtp")
    Call<MessageResponse> otpSend(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("user/verifyOtp")
    Call<MessageResponse> verifyOtp(
            @Field("otpReceived") String otpReceived,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("question/read")
    Call<FetchQuestionList> fetchQuestionList(
            @Field("orgCode") String orgCode
    );

    @FormUrlEncoded
    @POST("question/reply/read")
    Call<FetchRepliesOfAQuestion> fetchQuestionreply(
            @Field("orgCode") String orgCode,
            @Field("questionId") String questionId
    );

    @FormUrlEncoded
    @POST("user/changePassword")
    Call<MessageResponse> changePassword(
            @Field("email") String email,
            @Field("newPassword") String newPassword
    );

    @FormUrlEncoded
    @POST("notification/list")
    Call<FetchSubjectList> notificationTeacher(
            @Field("orgCode") String orgCode,
            @Field("role") String role,
            @Field("teacherCode") String teacherCode
    );

    @FormUrlEncoded
    @POST("notification/list")
    Call<FetchSubjectList> notificationStudent(
            @Field("orgCode") String orgCode,
            @Field("role") String role,
            @Field("studentClass") String studentClass,
            @Field("studentSection") String studentSection,
            @Field("studentRollNo") String studentRollNo
    );

    @FormUrlEncoded
    @POST("teacher/edit")
    Call<MessageResponse> updateTeacher(
            @Field("orgCode") String orgCode,
            @Field("teacherCode") String teacherCode,
            @Field("class_section_subject") ArrayList<String> class_section_subject
    );

    @FormUrlEncoded
    @POST("org/need")
    Call<StudentList> showStudentsList(
            @Field("orgCode") String orgCode,
            @Field("need") String need
    );

    @FormUrlEncoded
    @POST("org/need")
    Call<ClassList> showClassList(
            @Field("orgCode") String orgCode,
            @Field("need") String need
    );
}
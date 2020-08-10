package com.education.smartclass.api;

import com.education.smartclass.response.LoginResponse;
import com.education.smartclass.response.MessageResponse;
import com.education.smartclass.response.OrganisationList;

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
            @Field("class_section_subject") String class_section_subject,
            @Field("orgCode") String orgCode,
            @Field("methodToCreate") String methodToCreate
    );
}
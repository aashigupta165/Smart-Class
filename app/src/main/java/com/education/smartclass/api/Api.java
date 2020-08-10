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
    Call<MessageResponse> register(
            @Part("orgName") RequestBody orgName,
            @Part("orgCode") RequestBody orgCode,
            @Part("orgType") RequestBody orgType,
            @Part("orgAddress") RequestBody orgAddress,
            @Part("orgEmail") RequestBody orgEmail,
            @Part("role") RequestBody role,
            @Part("mobile") RequestBody mobile,
            @Part("methodToCreate") RequestBody methodToCreate,
            @Part("teacherName") RequestBody teacherName,
            @Part("teacherAge") RequestBody teacherAge,
            @Part("teacherDesignation") RequestBody teacherDesignation,
            @Part("teacherCode") RequestBody teacherCode,
            @Part("teacherGender") RequestBody teacherGender,
            @Part("teacherEmail") RequestBody teacherEmail,
            @Part("class_section_subject") RequestBody class_section_subject,
            @Part MultipartBody.Part file
    );
}
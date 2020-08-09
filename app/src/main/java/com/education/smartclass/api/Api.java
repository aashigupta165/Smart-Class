package com.education.smartclass.api;

import com.education.smartclass.response.LoginResponse;
import com.education.smartclass.response.MessageResponse;
import com.education.smartclass.response.OrganisationList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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

    @FormUrlEncoded
    @POST("register")
    Call<MessageResponse> register(
            @Field("orgName") String orgName,
            @Field("orgCode") String orgCode,
            @Field("orgType") String orgType,
            @Field("orgAddress") String orgAddress,
            @Field("orgEmail") String orgEmail,
            @Field("role") String role,
            @Field("mobile") String mobile,
            @Field("methodToCreate") String methodToCreate,
            @Field("file") String file
    );
}
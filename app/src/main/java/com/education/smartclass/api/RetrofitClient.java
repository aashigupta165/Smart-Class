package com.education.smartclass.api;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.education.smartclass.storage.SharedPrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String AUTH = "Basic " + Base64.encodeToString(("aashi:123456").getBytes(), Base64.NO_WRAP);
    private Context context;

    private static final String BASE_URL = "https://class-smart.herokuapp.com/api/app/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                if (SharedPrefManager.getInstance(context).getToken() == null) {
                                    Response response = chain.proceed(chain.request());
                                    String token = response.header("X-Auth");
                                    SharedPrefManager.getInstance(context)
                                            .saveToken(token);
                                    return response;
                                } else {
                                    String token = SharedPrefManager.getInstance(context).getToken();
                                    Log.d("token", token);
                                    Request original = chain.request();

                                    Request.Builder requestBuilder = original.newBuilder()
                                            .addHeader("x-auth", SharedPrefManager.getInstance(context).getToken())
                                            .method(original.method(), original.body());

                                    Request request = requestBuilder.build();
                                    return chain.proceed(request);
                                }
                            }
                        }
                ).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
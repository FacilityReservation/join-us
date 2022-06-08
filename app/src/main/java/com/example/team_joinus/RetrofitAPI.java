package com.example.team_joinus;
//same as JsonPlaceHolderApi java class in lecture

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @POST("users")
    Call<UserInfo> createUserInfo(@Body UserInfo userInfo);

}
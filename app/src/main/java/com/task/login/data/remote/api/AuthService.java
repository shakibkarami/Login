package com.task.login.data.remote.api;

import com.task.login.domain.model.TokenResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    @FormUrlEncoded
    @POST("login")
    Single<TokenResponse> authenticate(@Field("username") String username, @Field("password") String password);
}
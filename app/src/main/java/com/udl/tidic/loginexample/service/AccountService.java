package com.udl.tidic.loginexample.service;

import com.udl.tidic.loginexample.models.Account;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AccountService {
    @POST("account/create_token")
    Call<Account> createTokenUser(@Header("Authorization") String authorizationToken);
}

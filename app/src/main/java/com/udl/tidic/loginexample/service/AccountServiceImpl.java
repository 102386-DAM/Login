package com.udl.tidic.loginexample.service;

import com.udl.tidic.loginexample.models.Account;
import com.udl.tidic.loginexample.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Retrofit;

public class AccountServiceImpl implements AccountService {

    private Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<Account> createTokenUser(String authorizationToken) {
        return  retrofit.create(AccountService.class).createTokenUser(authorizationToken);
    }

}

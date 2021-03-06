package com.udl.tidic.loginexample.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.udl.tidic.loginexample.helpers.ApiCallback;
import com.udl.tidic.loginexample.models.Account;
import com.udl.tidic.loginexample.models.Result;
import com.udl.tidic.loginexample.service.AccountService;
import com.udl.tidic.loginexample.service.AccountServiceImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepo {

    private String TAG = "AccountRepo";

    private AccountService accountService;
    private Result<String> loginResult;
    private MutableLiveData<Result<String>> loginResultLiveData;

    public AccountRepo(){
        this.accountService = new AccountServiceImpl();
        loginResultLiveData = new MutableLiveData<>();

    }

    // Sends a login query to the backend
    public void login(String authorizationToken){
        this.accountService.createTokenUser(authorizationToken).enqueue(new ApiCallback<Account>() {

            @Override
            public void onFailure(Call call, Throwable t) {
                loginResult = Result.error(t);
                loginResultLiveData.postValue(loginResult);
                Log.d(TAG, "login() -> onResponseError -> " + t.getMessage());
            }

            @Override
            public void onResponseSuccess(Call<Account> call, Response<Account> response) {
                Log.d(TAG, "login() -> onResponseSuccess -> " + response.body().toString());
                String token = response.body().getToken();
                Log.d(TAG, "login() -> onResponseSuccess -> " + token);
                loginResult = Result.success(response.body().getToken());
                Log.d(TAG, "login() -> onResponseSuccess / getResult-> " + loginResult.getResult());
                loginResultLiveData.postValue(loginResult);
                Log.d(TAG, "login() -> onResponseSuccess END");
            }

            @Override
            public void onResponseError(Call<Account> call, Throwable t) {
                loginResult = Result.error(t);
                loginResultLiveData.postValue(loginResult);
                Log.d(TAG, "login() -> onResponseError -> " + t.getMessage());
            }
        });
    }

    public void login2(String authorizationToken) {
        this.accountService.createTokenUser(authorizationToken).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {

            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
    }

    // Gets the answer to login query
    public LiveData<Result<String>> getLoginResult(){
        return this.loginResultLiveData;
    }
}

package com.udl.tidic.loginexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.udl.tidic.loginexample.databinding.ActivityMainBinding;
import com.udl.tidic.loginexample.models.Result;
import com.udl.tidic.loginexample.usecases.LoginViewModel;
import com.udl.tidic.loginexample.utils.PreferencesProvider;
import com.udl.tidic.loginexample.utils.UIUtils;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    private LoginViewModel loginViewModel;
    private ActivityMainBinding activityLoginBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginViewModel = new LoginViewModel();
        initDataBinding();

        setup();
        data();

        loginViewModel.isUserLogged().observe(this, new Observer<Result<String>>() {
            @Override
            public void onChanged(Result<String> tokenResult) {
                loginViewModel.isLogged.postValue(false);
                if (tokenResult.getResult() != null){
                    Log.d(TAG,"Login successful, token obtained.");
                    PreferencesProvider.providePreferences().edit().putString("token",
                            tokenResult.getResult()).commit();
                    Log.d(TAG,"Login successful, add token to SharedPreferences.");
                    goTo(HomeActivity.class);
                }
                else{
                    //Display Error
                    Log.d(TAG,"User not logged, token not obtained.");
                    showLoginError(tokenResult.getError().getMessage());
                }
            }
        });

    }

    private void showLoginError(String errorMessage){
        DialogInterface.OnClickListener positiveAction = (dialogInterface, i) -> dialogInterface.cancel();
        UIUtils.showAlert(this,"Error", errorMessage, "Ok",positiveAction ,null,null, false);
    }

    private void initDataBinding() {
        activityLoginBinding =
                DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityLoginBinding.setLoginViewModel(loginViewModel);
        activityLoginBinding.setLifecycleOwner(this);
    }

    public void goTo(Class _class){
        Intent intent = new Intent(this, _class);
        startActivity(intent);
    }


    private void setup(){
        PreferencesProvider.init(this);
    }

    private void data(){
        String token = PreferencesProvider.providePreferences().getString("token", "");
        Log.d(TAG, "token: " + token);
        if (token.equals("")) {
            // If device has no token -> go to LoginActivity()
            //startActivity(new Intent(this, LoginActivity.class));
            //showLogin();
        } else {
            // If a userToken is stored on sharedPreferences go to MainActivity().
            startActivity(new Intent(this, HomeActivity.class));
        }
        // Close the activity, the user don't need to enter again with back functionality
        //finish();
    }

    /*private void showLogin(){
        LoginRouter loginRouter = new LoginRouter();
        loginRouter.launch(this);
    }*/


}
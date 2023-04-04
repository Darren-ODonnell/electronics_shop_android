package com.example.bottomnavigationproper.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Services.LoginRepository;
import com.example.bottomnavigationproper.User;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository loginRepository;
    private LiveData<Boolean> tokenValidityLiveData;




    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        loginRepository = new LoginRepository();
        tokenValidityLiveData = loginRepository.getTokenValidity();

    }

    public void login(Login login) {
        loginRepository.login(login);
    }

    public LiveData<Boolean> getTokenValidityLiveData() {
        return tokenValidityLiveData;
    }

    public void validateJWT(String token) {
        loginRepository.validateJWT(token);
    }
}

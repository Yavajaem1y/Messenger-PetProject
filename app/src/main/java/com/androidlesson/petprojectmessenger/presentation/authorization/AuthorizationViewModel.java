package com.androidlesson.petprojectmessenger.presentation.authorization;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.authorizationUseCase.AuthorizationError;
import com.androidlesson.domain.authorization.authorizationUseCase.CheckCurrentUserUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.LoginUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.models.LoginData;
import com.androidlesson.domain.authorization.models.RegistrationData;
import com.androidlesson.domain.authorization.repository.CallbackAuthorizaiton;

public class AuthorizationViewModel extends ViewModel {
    private LoginUseCase loginUseCase;
    private RegistrationUseCase registrationUseCase;
    private CheckCurrentUserUseCase checkCurrentUserUseCase;

    MutableLiveData<Boolean> goToMainActivityLiveData=new MutableLiveData<>();
    MutableLiveData<Integer> loginVisibilityLiveData =new MutableLiveData<Integer>();
    MutableLiveData<Integer> registrationVisibilitySceneLiveData=new MutableLiveData<Integer>();
    MutableLiveData<String> errorAuthorizationLiveData=new MutableLiveData<>();
    MutableLiveData<Boolean> progressBarSceneLiveData=new MutableLiveData<>();

    private CallbackAuthorizaiton callbackAuthorizaiton=new CallbackAuthorizaiton() {
        @Override
        public void taskIsSuccessful() {
            goToMainActivityLiveData.setValue(true);
            progressBarSceneLiveData.setValue(false);
        }

        @Override
        public void taskIsCanceled() {
            goToMainActivityLiveData.setValue(false);
            progressBarSceneLiveData.setValue(false);
        }
    };

    private AuthorizationError authorizationError=new AuthorizationError() {
        @Override
        public void showError(String errorMessage) {
            errorAuthorizationLiveData.setValue(errorMessage);
            progressBarSceneLiveData.setValue(false);
        }
    };


    public AuthorizationViewModel(LoginUseCase loginUseCase, RegistrationUseCase registrationUseCase, CheckCurrentUserUseCase checkCurrentUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.registrationUseCase = registrationUseCase;
        this.checkCurrentUserUseCase = checkCurrentUserUseCase;
    }

    public void login(LoginData user){
        progressBarSceneLiveData.setValue(true);
        loginUseCase.execute(user,callbackAuthorizaiton,authorizationError);
    }

    public void registration(RegistrationData user){
        progressBarSceneLiveData.setValue(true);
        registrationUseCase.execute(user,callbackAuthorizaiton,authorizationError);
    }

    public void checkCurrentUser(){
        goToMainActivityLiveData.setValue(checkCurrentUserUseCase.execute());
    }

    public void loginScene(){
        loginVisibilityLiveData.setValue(View.VISIBLE);
        registrationVisibilitySceneLiveData.setValue(View.INVISIBLE);
    }

    public void registerScene(){
        loginVisibilityLiveData.setValue(View.INVISIBLE);
        registrationVisibilitySceneLiveData.setValue(View.VISIBLE);
    }
}

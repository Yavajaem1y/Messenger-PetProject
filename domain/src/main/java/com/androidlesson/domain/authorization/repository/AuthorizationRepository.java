package com.androidlesson.domain.authorization.repository;


import com.androidlesson.domain.authorization.authorizationUseCase.AuthorizationError;
import com.androidlesson.domain.authorization.models.LoginData;
import com.androidlesson.domain.authorization.models.RegistrationData;

public interface AuthorizationRepository {
    public void login(LoginData logData, CallbackAuthorizaiton callback, AuthorizationError authorizationError);
    public void registation(RegistrationData regData,CallbackAuthorizaiton callback,AuthorizationError authorizationError);
    public boolean checkCurrUser();
}

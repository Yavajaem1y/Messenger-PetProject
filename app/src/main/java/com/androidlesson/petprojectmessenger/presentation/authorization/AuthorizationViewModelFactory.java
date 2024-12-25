package com.androidlesson.petprojectmessenger.presentation.authorization;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.authorization.authorizationUseCase.CheckCurrentUserUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.LoginUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.RegistrationUseCase;

public class AuthorizationViewModelFactory implements ViewModelProvider.Factory {
    private LoginUseCase loginUseCase;
    private RegistrationUseCase registrationUseCase;
    private CheckCurrentUserUseCase checkCurrentUserUseCase;

    public AuthorizationViewModelFactory(LoginUseCase loginUseCase, RegistrationUseCase registrationUseCase, CheckCurrentUserUseCase checkCurrentUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.registrationUseCase = registrationUseCase;
        this.checkCurrentUserUseCase = checkCurrentUserUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AuthorizationViewModel(loginUseCase,registrationUseCase,checkCurrentUserUseCase);
    }
}

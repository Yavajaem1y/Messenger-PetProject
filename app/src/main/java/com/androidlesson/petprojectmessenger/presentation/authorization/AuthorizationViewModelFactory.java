package com.androidlesson.petprojectmessenger.presentation.authorization;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.data.authorization.AuthotizationRepositoryImpl;
import com.androidlesson.domain.authorization.authorizationUseCase.CheckCurrentUserUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.LoginUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.repository.AuthorizationRepository;

public class AuthorizationViewModelFactory implements ViewModelProvider.Factory {
    private AuthorizationRepository authorizationRepository;
    private LoginUseCase loginUseCase;
    private RegistrationUseCase registrationUseCase;
    private CheckCurrentUserUseCase checkCurrentUserUseCase;

    public AuthorizationViewModelFactory() {
        authorizationRepository=new AuthotizationRepositoryImpl();
        loginUseCase=new LoginUseCase(authorizationRepository);
        registrationUseCase=new RegistrationUseCase(authorizationRepository);
        checkCurrentUserUseCase=new CheckCurrentUserUseCase(authorizationRepository);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AuthorizationViewModel(loginUseCase,registrationUseCase,checkCurrentUserUseCase);
    }
}

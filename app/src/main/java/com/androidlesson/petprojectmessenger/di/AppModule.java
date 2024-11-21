package com.androidlesson.petprojectmessenger.di;

import android.content.Context;

import com.androidlesson.domain.authorization.authorizationUseCase.CheckCurrentUserUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.LoginUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.RegistrationUseCase;
import com.androidlesson.petprojectmessenger.presentation.authorization.AuthorizationViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    //Get context
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    //AuthorizationViewModelFactory
    @Provides
    public AuthorizationViewModelFactory provideAuthorizationViewModelFactory(LoginUseCase loginUseCase, RegistrationUseCase registrationUseCase, CheckCurrentUserUseCase checkCurrentUserUseCase){
        return new AuthorizationViewModelFactory(loginUseCase,registrationUseCase,checkCurrentUserUseCase);
    }
}

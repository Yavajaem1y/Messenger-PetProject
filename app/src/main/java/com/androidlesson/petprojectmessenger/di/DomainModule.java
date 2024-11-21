package com.androidlesson.petprojectmessenger.di;

import com.androidlesson.domain.authorization.authorizationUseCase.CheckCurrentUserUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.LoginUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.repository.AuthorizationRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

    //Authorization UseCases
    @Provides
    public LoginUseCase providesLoginUseCase(AuthorizationRepository authorizationRepository){
        return new LoginUseCase(authorizationRepository);
    }

    @Provides
    public CheckCurrentUserUseCase providesCheckCurrentUserUseCase(AuthorizationRepository authorizationRepository){
        return new CheckCurrentUserUseCase(authorizationRepository);
    }

    @Provides
    public RegistrationUseCase RegistrationUseCase(AuthorizationRepository authorizationRepository){
        return new RegistrationUseCase(authorizationRepository);
    }
}

package com.androidlesson.petprojectmessenger.di;

import com.androidlesson.data.authorization.AuthotizationRepositoryImpl;
import com.androidlesson.domain.authorization.authorizationUseCase.CheckCurrentUserUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.LoginUseCase;
import com.androidlesson.domain.authorization.authorizationUseCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.repository.AuthorizationRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    //Main Repository
    @Provides
    public AuthorizationRepository providesAuthorizationRepository(){
        return new AuthotizationRepositoryImpl();
    }
}

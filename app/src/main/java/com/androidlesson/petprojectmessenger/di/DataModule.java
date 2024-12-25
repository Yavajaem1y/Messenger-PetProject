package com.androidlesson.petprojectmessenger.di;

import android.content.Context;

import com.androidlesson.data.authorization.AuthotizationRepositoryImpl;
import com.androidlesson.data.main.repository.MainFirebaseRepositoryImpl;
import com.androidlesson.data.main.repository.MainSharedPrefRepositoryImpl;
import com.androidlesson.domain.authorization.repository.AuthorizationRepository;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPrefRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    //Authorization Repository
    @Provides
    public AuthorizationRepository providesAuthorizationRepository(){
        return new AuthotizationRepositoryImpl();
    }

    //Main Repository
    @Provides
    public MainFirebaseRepository providesMainFirebaseRepository(){
        return new MainFirebaseRepositoryImpl();
    }

    @Provides
    public MainSharedPrefRepository providesMainSharedPrefRepository(Context context){
        return new MainSharedPrefRepositoryImpl(context);
    }

}

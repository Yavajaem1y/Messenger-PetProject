package com.androidlesson.petprojectmessenger.di;

import com.androidlesson.petprojectmessenger.presentation.authorization.AuthorizationActivity;

import dagger.Component;

@Component(modules = {AppModule.class, DomainModule.class, DataModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        Builder appModule(AppModule appModule);
        AppComponent build();
    }

    void inject(AuthorizationActivity authorizationActivity);
}

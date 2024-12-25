package com.androidlesson.petprojectmessenger.app;

import android.app.Application;

import com.androidlesson.petprojectmessenger.di.AppComponent;
import com.androidlesson.petprojectmessenger.di.AppModule;
import com.androidlesson.petprojectmessenger.di.DaggerAppComponent;

public class App extends Application {

    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}

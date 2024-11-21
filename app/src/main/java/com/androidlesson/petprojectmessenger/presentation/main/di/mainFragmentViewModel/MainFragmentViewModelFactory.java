package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackLogOut;

public class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    private UserData userData;
    private CallbackLogOut callbackLogOut;

    public MainFragmentViewModelFactory(UserData userData,CallbackLogOut callbackLogOut) {
        this.userData=userData;
        this.callbackLogOut=callbackLogOut;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainFragmentViewModel(userData,callbackLogOut);
    }
}
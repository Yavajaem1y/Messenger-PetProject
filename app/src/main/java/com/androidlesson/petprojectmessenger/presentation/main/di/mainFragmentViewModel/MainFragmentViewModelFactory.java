package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserDataFromDB;

public class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    private CurrentUserDataFromDB currentUserDataFromDB;

    public MainFragmentViewModelFactory(CurrentUserDataFromDB currentUserDataFromDB) {
        this.currentUserDataFromDB = currentUserDataFromDB;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainFragmentViewModel(currentUserDataFromDB);
    }
}
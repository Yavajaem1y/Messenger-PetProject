package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainFragmentViewModelFactory implements ViewModelProvider.Factory {


    public MainFragmentViewModelFactory() {

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainFragmentViewModel();
    }
}
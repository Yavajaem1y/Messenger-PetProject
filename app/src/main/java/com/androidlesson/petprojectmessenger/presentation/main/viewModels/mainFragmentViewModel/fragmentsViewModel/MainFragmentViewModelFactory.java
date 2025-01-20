package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.MainFragmentViewModel;

public class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    public MainFragmentViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainFragmentViewModel();
    }
}

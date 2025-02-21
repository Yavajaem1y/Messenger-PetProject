package com.androidlesson.petprojectmessenger.presentation.main.viewModels.CurrentUserProfileFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CurrentUserProfileViewModelFactory implements ViewModelProvider.Factory {


    public CurrentUserProfileViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CurrentUserProfileViewModel();
    }
}

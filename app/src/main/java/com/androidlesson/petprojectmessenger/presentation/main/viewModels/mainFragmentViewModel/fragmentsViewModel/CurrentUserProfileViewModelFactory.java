package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.models.UserData;

public class CurrentUserProfileViewModelFactory implements ViewModelProvider.Factory {


    public CurrentUserProfileViewModelFactory(Context context) {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CurrentUserProfileViewModel();
    }
}

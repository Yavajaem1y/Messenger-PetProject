package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.fragmentsViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackLogOut;

public class CurrentUserProfileViewModelFactory implements ViewModelProvider.Factory {

    private UserData userData;

    public CurrentUserProfileViewModelFactory(Context context, UserData userData) {
        this.userData=userData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CurrentUserProfileViewModel(userData);
    }
}

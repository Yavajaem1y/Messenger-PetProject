package com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.usecase.ObserveCurrentUserDataUseCase;
import com.androidlesson.domain.main.usecase.UserAvatarImageListener;

public class SharedViewModelFactory implements ViewModelProvider.Factory {
    private final ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase;
    private final UserAvatarImageListener userAvatarImageListener;

    public SharedViewModelFactory(ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase, UserAvatarImageListener userAvatarImageListener) {
        this.observeCurrentUserDataUseCase = observeCurrentUserDataUseCase;
        this.userAvatarImageListener = userAvatarImageListener;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SharedViewModel(observeCurrentUserDataUseCase,userAvatarImageListener);
    }
}

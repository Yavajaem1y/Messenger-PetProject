package com.androidlesson.petprojectmessenger.presentation.main.viewModels.sharedViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.usecase.ObserveCurrentUserDataUseCase;

public class SharedViewModelFactory implements ViewModelProvider.Factory {
    private final ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase;

    public SharedViewModelFactory(ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase) {
        this.observeCurrentUserDataUseCase = observeCurrentUserDataUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SharedViewModel(observeCurrentUserDataUseCase);
    }
}

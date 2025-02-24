package com.androidlesson.petprojectmessenger.presentation.main.viewModels.editUserDataActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.usecase.EditUserDataUseCase;

public class EditUserDataActivityViewModelFactory implements ViewModelProvider.Factory {

    private EditUserDataUseCase editUserDataUseCase;

    public EditUserDataActivityViewModelFactory(EditUserDataUseCase editUserDataUseCase) {
        this.editUserDataUseCase = editUserDataUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditUserDataActivityViewModel(editUserDataUseCase);
    }
}

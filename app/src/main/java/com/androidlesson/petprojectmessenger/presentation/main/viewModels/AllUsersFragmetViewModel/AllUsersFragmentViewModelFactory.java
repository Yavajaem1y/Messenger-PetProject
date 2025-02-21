package com.androidlesson.petprojectmessenger.presentation.main.viewModels.AllUsersFragmetViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.usecase.LoadAllUserUseCase;

public class AllUsersFragmentViewModelFactory implements ViewModelProvider.Factory {

    private LoadAllUserUseCase loadAllUserUseCase;

    public AllUsersFragmentViewModelFactory(LoadAllUserUseCase loadAllUserUseCase) {
        this.loadAllUserUseCase = loadAllUserUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AllUsersFragmentViewModel(loadAllUserUseCase);
    }
}

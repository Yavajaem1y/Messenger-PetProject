package com.androidlesson.petprojectmessenger.presentation.main.viewModels.AllChatsFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.usecase.LoadAllChatsUseCase;

public class AllChatsFragmentViewModelFactory implements ViewModelProvider.Factory {
    private final LoadAllChatsUseCase loadAllChatsUseCase;

    public AllChatsFragmentViewModelFactory(LoadAllChatsUseCase loadAllChatsUseCase) {
        this.loadAllChatsUseCase = loadAllChatsUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AllChatsFragmentViewModel(loadAllChatsUseCase);
    }
}

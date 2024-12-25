package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainActivityViewModel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.data.main.repository.MainFirebaseRepositoryImpl;
import com.androidlesson.data.main.repository.MainSharedPrefRepositoryImpl;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPrefRepository;
import com.androidlesson.domain.main.usecase.LoadUserDataUseCase;
import com.androidlesson.domain.main.usecase.LogOutUseCase;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private LoadUserDataUseCase loadUserDataUseCase;
    private LogOutUseCase logOutUseCase;

    public MainActivityViewModelFactory(LoadUserDataUseCase loadUserDataUseCase, LogOutUseCase logOutUseCase) {
        this.loadUserDataUseCase = loadUserDataUseCase;
        this.logOutUseCase = logOutUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(loadUserDataUseCase,logOutUseCase);
    }
}

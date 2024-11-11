package com.androidlesson.petprojectmessenger.presentation.main.di.mainActivityViewModel;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.data.main.repositoryForUseCase.MainFirebaseRepositoryImpl;
import com.androidlesson.data.main.repositoryForUseCase.MainSharedPreferencesRepositoryImpl;
import com.androidlesson.domain.main.mainUseCase.SaveCurrentUserDataUseCase;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPreferencesRepository;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final MainSharedPreferencesRepository mainSharedPreferencesRepository;
    private final MainFirebaseRepository mainFirebaseRepository;
    private final SaveCurrentUserDataUseCase saveCurrentUserDataUseCase;

    public MainActivityViewModelFactory(Context context) {
        mainSharedPreferencesRepository = new MainSharedPreferencesRepositoryImpl(context);
        mainFirebaseRepository=new MainFirebaseRepositoryImpl();
        saveCurrentUserDataUseCase=new SaveCurrentUserDataUseCase(mainSharedPreferencesRepository,mainFirebaseRepository);

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(mainSharedPreferencesRepository,mainFirebaseRepository,saveCurrentUserDataUseCase);
    }
}

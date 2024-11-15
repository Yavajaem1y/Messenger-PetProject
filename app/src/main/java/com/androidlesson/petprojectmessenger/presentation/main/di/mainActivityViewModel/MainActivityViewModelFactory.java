package com.androidlesson.petprojectmessenger.presentation.main.di.mainActivityViewModel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.data.main.repository.MainFirebaseRepositoryImpl;
import com.androidlesson.data.main.repository.MainSharedPrefRepositoryImpl;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPrefRepository;
import com.androidlesson.domain.main.usecase.LoadUserDataUseCase;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private MainFirebaseRepository firebaseRepository;
    private MainSharedPrefRepository sharedPrefRepository;

    private LoadUserDataUseCase loadUserDataUseCase;

    public MainActivityViewModelFactory(Context context) {
        firebaseRepository=new MainFirebaseRepositoryImpl();
        sharedPrefRepository=new MainSharedPrefRepositoryImpl(context);

        loadUserDataUseCase=new LoadUserDataUseCase(firebaseRepository,sharedPrefRepository);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(loadUserDataUseCase);
    }
}

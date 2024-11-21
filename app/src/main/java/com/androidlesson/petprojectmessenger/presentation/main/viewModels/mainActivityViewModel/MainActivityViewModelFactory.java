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

    private MainFirebaseRepository firebaseRepository;
    private MainSharedPrefRepository sharedPrefRepository;

    private LoadUserDataUseCase loadUserDataUseCase;
    private LogOutUseCase logOutUseCase;

    public MainActivityViewModelFactory(Context context) {
        firebaseRepository=new MainFirebaseRepositoryImpl();
        sharedPrefRepository=new MainSharedPrefRepositoryImpl(context);

        loadUserDataUseCase=new LoadUserDataUseCase(firebaseRepository,sharedPrefRepository);
        logOutUseCase=new LogOutUseCase(firebaseRepository,sharedPrefRepository);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(loadUserDataUseCase,logOutUseCase);
    }
}

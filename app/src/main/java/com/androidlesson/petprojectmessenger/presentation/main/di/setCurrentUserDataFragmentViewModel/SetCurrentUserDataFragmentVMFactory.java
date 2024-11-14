package com.androidlesson.petprojectmessenger.presentation.main.di.setCurrentUserDataFragmentViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.data.main.repository.MainFirebaseRepositoryImpl;
import com.androidlesson.data.main.repository.MainSharedPrefRepositoryImpl;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPrefRepository;
import com.androidlesson.domain.main.usecase.SaveUserDataUseCase;

public class SetCurrentUserDataFragmentVMFactory implements ViewModelProvider.Factory {

    private MainSharedPrefRepository sharedPrefRepository;
    private MainFirebaseRepository firebaseRepository;

    private SaveUserDataUseCase saveUserDataUseCase;

    public SetCurrentUserDataFragmentVMFactory(Context context) {
        sharedPrefRepository=new MainSharedPrefRepositoryImpl(context);
        firebaseRepository=new MainFirebaseRepositoryImpl();

        saveUserDataUseCase=new SaveUserDataUseCase(firebaseRepository,sharedPrefRepository);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SetCurrentUserDataFragmentVM(saveUserDataUseCase);
    }
}

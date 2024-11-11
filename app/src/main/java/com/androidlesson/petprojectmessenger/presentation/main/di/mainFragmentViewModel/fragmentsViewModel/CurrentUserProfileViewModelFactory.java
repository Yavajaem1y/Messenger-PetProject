package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.fragmentsViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.data.main.repositoryForUseCase.MainFirebaseRepositoryImpl;
import com.androidlesson.data.main.repositoryForUseCase.MainSharedPreferencesRepositoryImpl;
import com.androidlesson.domain.main.mainUseCase.LogOutUseCase;
import com.androidlesson.domain.main.mainUseCase.SetUserProfileAvatarUseCase;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPreferencesRepository;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserAvatarFromMainVM;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserDataFromDB;

public class CurrentUserProfileViewModelFactory implements ViewModelProvider.Factory {

    private CurrentUserAvatarFromMainVM avatarCallback;
    private CurrentUserDataFromDB currentUserDataFromDB;

    private MainSharedPreferencesRepository mainSharedPreferencesRepository;
    private MainFirebaseRepository mainFirebaseRepository;
    private SetUserProfileAvatarUseCase setUserProfileAvatarUseCase;
    private LogOutUseCase logOutUseCase;

    public CurrentUserProfileViewModelFactory(Context context,
                                              CurrentUserAvatarFromMainVM currentUserAvatarFromMainVM,
                                              CurrentUserDataFromDB currentUserDataFromDB) {
        mainFirebaseRepository=new MainFirebaseRepositoryImpl();
        mainSharedPreferencesRepository= new MainSharedPreferencesRepositoryImpl(context);
        setUserProfileAvatarUseCase =new SetUserProfileAvatarUseCase(mainSharedPreferencesRepository,mainFirebaseRepository);
        avatarCallback= currentUserAvatarFromMainVM;
        this.currentUserDataFromDB = currentUserDataFromDB;
        logOutUseCase=new LogOutUseCase(mainSharedPreferencesRepository,mainFirebaseRepository);

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CurrentUserProfileViewModel(avatarCallback, currentUserDataFromDB,mainSharedPreferencesRepository,mainFirebaseRepository, setUserProfileAvatarUseCase,logOutUseCase);
    }
}

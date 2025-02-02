package com.androidlesson.petprojectmessenger.presentation.main.viewModels.anotherUserProfileActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.usecase.AddToFriendsUseCase;
import com.androidlesson.domain.main.usecase.GoToChatViewUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataByIdUseCase;

public class AnotherUserProfileActivityViewModelFactory implements ViewModelProvider.Factory {

    private AddToFriendsUseCase addToFriendsUseCase;
    private LoadUserDataByIdUseCase loadUserDataByIdUseCase;
    private GoToChatViewUseCase goToChatViewUseCase;

    public AnotherUserProfileActivityViewModelFactory(AddToFriendsUseCase addToFriendsUseCase, LoadUserDataByIdUseCase loadUserDataByIdUseCase,GoToChatViewUseCase goToChatViewUseCase) {
        this.addToFriendsUseCase = addToFriendsUseCase;
        this.loadUserDataByIdUseCase = loadUserDataByIdUseCase;
        this.goToChatViewUseCase=goToChatViewUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnotherUserProfileActivityViewModel(addToFriendsUseCase,loadUserDataByIdUseCase,goToChatViewUseCase);
    }
}

package com.androidlesson.petprojectmessenger.presentation.main.viewModels.anotherUserProfileActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.usecase.AddToFriendsUseCase;

public class AnotherUserProfileActivityViewModelFactory implements ViewModelProvider.Factory {

    private AddToFriendsUseCase addToFriendsUseCase;

    public AnotherUserProfileActivityViewModelFactory(AddToFriendsUseCase addToFriendsUseCase) {
        this.addToFriendsUseCase = addToFriendsUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnotherUserProfileActivityViewModel(addToFriendsUseCase);
    }
}

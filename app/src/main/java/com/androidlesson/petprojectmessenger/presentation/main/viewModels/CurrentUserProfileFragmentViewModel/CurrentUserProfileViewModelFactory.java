package com.androidlesson.petprojectmessenger.presentation.main.viewModels.CurrentUserProfileFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.usecase.UploadImageAvatarUseCase;

public class CurrentUserProfileViewModelFactory implements ViewModelProvider.Factory {

    private UploadImageAvatarUseCase uploadImageAvatarUseCase;

    public CurrentUserProfileViewModelFactory(UploadImageAvatarUseCase uploadImageAvatarUseCase) {
        this.uploadImageAvatarUseCase = uploadImageAvatarUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CurrentUserProfileViewModel(uploadImageAvatarUseCase);
    }
}

package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks;

public interface CurrentUserAvatarFromMainVM {
    public void setUserAvatar(byte[] image);
    public byte[] getUserAvatar();
}

package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.interfaces.OnImageUrlFetchedListener;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class UserAvatarImageListener {

    private MainFirebaseRepository mainFirebaseRepository;

    public UserAvatarImageListener(MainFirebaseRepository mainFirebaseRepository) {
        this.mainFirebaseRepository = mainFirebaseRepository;
    }

    public void execute(String userId, OnImageUrlFetchedListener onImageUrlFetchedListener){
        if (userId!=null && !userId.isEmpty() && onImageUrlFetchedListener!=null){
            mainFirebaseRepository.userAvatarListenerById(userId,onImageUrlFetchedListener);
        }
    }
}

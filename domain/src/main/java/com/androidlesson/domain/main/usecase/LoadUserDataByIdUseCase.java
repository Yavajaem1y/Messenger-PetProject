package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.interfaces.CallbackGetUserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class LoadUserDataByIdUseCase {
    private final MainFirebaseRepository firebaseRepository;

    public LoadUserDataByIdUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(String id, CallbackGetUserData callbackGetUserData){
        firebaseRepository.getUserDataById(id, callbackGetUserData);
    }
}

package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.interfaces.CallbackGetUserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class ObserveCurrentUserDataUseCase {
    private MainFirebaseRepository mainFirebaseRepository;

    public ObserveCurrentUserDataUseCase(MainFirebaseRepository mainFirebaseRepository) {
        this.mainFirebaseRepository = mainFirebaseRepository;
    }


    public void execute(CallbackGetUserData callbackGetUserData){
        mainFirebaseRepository.observeUserData(callbackGetUserData);
    }
}

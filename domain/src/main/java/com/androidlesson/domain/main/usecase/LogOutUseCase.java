package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPrefRepository;

public class LogOutUseCase {
    private MainFirebaseRepository firebaseRepository;
    private MainSharedPrefRepository sharedPrefRepository;

    public LogOutUseCase(MainFirebaseRepository firebaseRepository, MainSharedPrefRepository sharedPrefRepository) {
        this.firebaseRepository = firebaseRepository;
        this.sharedPrefRepository = sharedPrefRepository;
    }

    public void execute(){
        sharedPrefRepository.logOut();
        firebaseRepository.logOut();
    }
}

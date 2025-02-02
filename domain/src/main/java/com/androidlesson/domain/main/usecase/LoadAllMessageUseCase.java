package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class LoadAllMessageUseCase {
    private MainFirebaseRepository firebaseRepository;

    public LoadAllMessageUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }
}

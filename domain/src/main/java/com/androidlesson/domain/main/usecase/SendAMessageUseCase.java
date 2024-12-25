package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class SendAMessageUseCase {

    private MainFirebaseRepository firebaseRepository;

    public SendAMessageUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(UserData anotherUser, UserData currUser) {

    }
}

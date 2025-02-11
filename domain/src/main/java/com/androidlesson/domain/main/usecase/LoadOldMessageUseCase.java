package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class LoadOldMessageUseCase {
    private MainFirebaseRepository firebaseRepository;

    public LoadOldMessageUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public Observable<List<ChatInfo.Message>> execute(String lastMessageTimestamp, String chatId) {
        return firebaseRepository.loadOldMessages(lastMessageTimestamp,chatId);
    }
}

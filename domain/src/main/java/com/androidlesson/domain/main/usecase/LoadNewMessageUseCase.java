package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.callbacks.CallbackWithChatInfo;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class LoadNewMessageUseCase {

    private MainFirebaseRepository firebaseRepository;

    public LoadNewMessageUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(String chatId,CallbackWithChatInfo callbackWithChatInfo){
        if (callbackWithChatInfo!=null && chatId!=null && !chatId.isEmpty()){
            firebaseRepository.loadNewMessage(chatId,callbackWithChatInfo);
        }
    }
}

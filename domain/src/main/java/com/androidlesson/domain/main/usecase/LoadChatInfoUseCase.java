package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.interfaces.CallbackWithChatInfo;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class LoadChatInfoUseCase {
    private MainFirebaseRepository firebaseRepository;

    public LoadChatInfoUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(String chatId, CallbackWithChatInfo callbackWithChatInfo){
        if (chatId!=null && callbackWithChatInfo!=null){
            firebaseRepository.getChatInfoById(chatId,callbackWithChatInfo);
        }
    }
}

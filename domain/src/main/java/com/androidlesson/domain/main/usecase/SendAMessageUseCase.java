package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.utils.CurrentTimeAndDate;

public class SendAMessageUseCase {

    private MainFirebaseRepository firebaseRepository;

    public SendAMessageUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(String message,ChatInfo chatInfo, UserData currentUserData) {
        if (message!=null) {
            message = message.trim();
            if (chatInfo != null && currentUserData != null && !message.isEmpty()) {
                chatInfo.pushNewMessage(new CurrentTimeAndDate().getCurrentTime());
                ChatInfo.Message messageToDb = new ChatInfo.Message(currentUserData.getUserId(), message, chatInfo.getTimeLastMessage());
                firebaseRepository.sendAMessageUseCase(messageToDb, chatInfo);
            }
        }
    }
}

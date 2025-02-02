package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.callbacks.CallbackWithChatInfo;
import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GoToChatViewUseCase {
    private MainFirebaseRepository firebaseRepository;

    public GoToChatViewUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(UserData firstUser, UserData secondUser, CallbackWithChatInfo callbackWithChatInfo){
        String chatId=getChatId(firstUser.getUserSystemId(),secondUser.getUserSystemId());
        if (chatId!=null){
            if (firstUser.getChatIds().contains(chatId)) {
                callbackWithChatInfo.getChatId(chatId);
            }
            else{
                firebaseRepository.goToChatView(new ChatInfo(chatId,firstUser.getUserId(),secondUser.getUserId(),null,null,firstUser.getChatIds(),secondUser.getChatIds()),callbackWithChatInfo);
            }
        }
    }

    private String getChatId(String idFirstUser, String idSecondUser) {
        if (idFirstUser.compareTo(idSecondUser) > 0) {
            return idFirstUser + idSecondUser;
        } else {
            return idSecondUser + idFirstUser;
        }
    }
}

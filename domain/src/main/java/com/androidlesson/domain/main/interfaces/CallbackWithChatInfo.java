package com.androidlesson.domain.main.interfaces;

import com.androidlesson.domain.main.models.ChatInfo;

public interface CallbackWithChatInfo {
    public void getChatId(String chatId);
    public void getChatInfo(ChatInfo chatInfo);
    public void getMessage(ChatInfo.Message message);
}

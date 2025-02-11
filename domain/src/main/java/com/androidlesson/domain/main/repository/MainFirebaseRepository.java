package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.main.callbacks.CallbackCheckAvailableIds;
import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.callbacks.CallbackWithChatInfo;
import com.androidlesson.domain.main.callbacks.CallbackWithId;
import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MainFirebaseRepository {
    public void getCurrentUserData(CallbackGetUserData callbackGetUserData);
    public void getUserDataById(String id,CallbackGetUserData callbackGetUserData);
    public void saveUserData(UserData userInfo, CallbackGetUserData callbackGetUserData);
    public void checkAvailableIds(String id, CallbackCheckAvailableIds checkAvailableIds);
    public void getBasicId(CallbackWithId callbackWithId);
    public void logOut();
    public Observable<List<UserData>> loadAllUser(String lastKey, int limit);
    public void addFriend(UserData currUser,UserData anotherUser);
    public void subscribeOnUser(UserData currUser,UserData anotherUser);
    public void observeUserData(CallbackGetUserData callbackGetUserData);
    public void goToChatView(ChatInfo chatInfo, CallbackWithChatInfo callbackWithChatInfo);
    public void getChatInfoById(String chatId,CallbackWithChatInfo callbackWithChatInfo);
    public void sendAMessageUseCase(ChatInfo.Message message,ChatInfo chatInfo);
    public Observable<List<ChatInfo.Message>> loadOldMessages(String lastMessageTimestamp, String chatId);
    public void loadNewMessage(String chatId, CallbackWithChatInfo callbackWithChatInfo);
}

package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.main.callbacks.CallbackCheckAvailableIds;
import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.callbacks.CallbackWithId;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MainFirebaseRepository {
    public void getUserData(CallbackGetUserData callbackGetUserData);
    public void saveUserData(UserData userInfo, CallbackGetUserData callbackGetUserData);
    public void checkAvailableIds(String id, CallbackCheckAvailableIds checkAvailableIds);
    public void getBasicId(CallbackWithId callbackWithId);
    public void logOut();
    public Observable<List<UserData>> loadAllUser(String lastKey, int limit);
    public void addFriend(UserData currUser,UserData anotherUser);
    public void subscribeOnUser(UserData anotherUser);
}

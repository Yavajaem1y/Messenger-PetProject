package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.models.UserNameAndSurname;

public interface MainFirebaseRepository {
    public void getUserData(CallbackGetUserData callbackGetUserData);
    public void saveUserData(UserNameAndSurname userNameAndSurname, CallbackGetUserData callbackGetUserData);
}

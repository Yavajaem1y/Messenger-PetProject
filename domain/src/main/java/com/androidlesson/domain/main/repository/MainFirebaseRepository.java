package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.main.models.FullUserData;
import com.androidlesson.domain.main.repository.callbacks.CallbackCurrentUserInfoFromFireBase;

public interface MainFirebaseRepository {
    public boolean logout();
    public boolean setCurrentUserData(FullUserData fullUserData);
    public void getCurrentUserData(CallbackCurrentUserInfoFromFireBase callbackCurrentUserInfoFromFireBase);
    public String getCurrentUserId();
    public void setCurrnetUserAvatar(byte[] image);
}

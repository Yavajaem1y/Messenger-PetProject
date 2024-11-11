package com.androidlesson.domain.main.repository.callbacks;

import com.androidlesson.domain.main.models.FullUserData;

public interface CallbackCurrentUserInfoFromFireBase {
    public void callbackUserInfo(FullUserData fullUserData);
}

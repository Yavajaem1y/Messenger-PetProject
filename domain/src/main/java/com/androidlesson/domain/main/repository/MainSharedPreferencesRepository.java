package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.main.models.FullUserData;
import com.androidlesson.domain.main.models.UserNameAndSurname;
import com.androidlesson.domain.main.repository.callbacks.CallbackSaveUserData;

public interface MainSharedPreferencesRepository {
    public FullUserData loadUserData();
    public UserNameAndSurname getUserNameAndSurname();
    public void refreshCurrentUserNameAndSurname(UserNameAndSurname userNameAndSurname);
    public void saveUserData(FullUserData fullUserData, CallbackSaveUserData callbackSaveUserData);
    public void clearUserData();
}

package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserNameAndSurname;

public interface MainSharedPrefRepository {
    public UserData getUserData();
    public void saveUserData(UserData userData);
}

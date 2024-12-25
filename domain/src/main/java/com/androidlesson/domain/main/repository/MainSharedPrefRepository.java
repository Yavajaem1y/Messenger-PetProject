package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.main.models.UserData;

public interface MainSharedPrefRepository {
    public UserData getUserData();
    public void saveUserData(UserData userData);
    public void logOut();
}

package com.androidlesson.petprojectmessenger.presentation.main.model;

import com.androidlesson.domain.main.models.UserData;

import java.io.Serializable;

public class SerializableUserData implements Serializable {

    private UserData userData;

    public SerializableUserData(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }
}

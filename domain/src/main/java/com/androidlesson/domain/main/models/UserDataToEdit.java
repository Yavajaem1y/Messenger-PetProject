package com.androidlesson.domain.main.models;

public class UserDataToEdit {
    String userName,userSurname,userInfo;

    public UserDataToEdit() {
    }

    public UserDataToEdit(String userName, String userSurname, String userInfo) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userInfo = userInfo;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getUserInfo() {
        return userInfo;
    }

}

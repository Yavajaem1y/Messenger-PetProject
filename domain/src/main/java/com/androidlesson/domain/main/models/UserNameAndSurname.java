package com.androidlesson.domain.main.models;

public class UserNameAndSurname {
    private String userName,userSurname;

    public UserNameAndSurname(String userName, String userSurname) {
        this.userName = userName;
        this.userSurname = userSurname;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }
}

package com.androidlesson.domain.main.models;

public class FullUserData {
    private String userID, userName, userSurname;

    public FullUserData() {
    }

    public FullUserData(String userID) {
        this.userID = userID;
        userName="";
        userSurname="";
    }

    public FullUserData(String userName, String userSurname) {
        this.userName = userName;
        this.userSurname = userSurname;
    }

    public FullUserData(String userID, String userName, String userSurname) {
        this.userID = userID;
        this.userName = userName;
        this.userSurname = userSurname;
    }


    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
}

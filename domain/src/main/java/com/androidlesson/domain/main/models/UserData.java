package com.androidlesson.domain.main.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable{
    private String userId,userName,userSurname;
    private List<String> friendsIds,taskToFriendsIds;

    public UserData(String userId, String userName, String userSurname, List<String> friendsIds, List<String> taskToFriendsIds) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.friendsIds = friendsIds;
        this.taskToFriendsIds = taskToFriendsIds;
    }

    public UserData(String userId, String userSurname, String userName) {
        this.userId = userId;
        this.userSurname = userSurname;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public List<String> getFriendsIds() {
        return friendsIds;
    }

    public List<String> getTaskToFriendsIds() {
        return taskToFriendsIds;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTaskToFriendsIds(List<String> taskToFriendsIds) {
        this.taskToFriendsIds = taskToFriendsIds;
    }

    public void setFriendsIds(List<String> friendsIds) {
        this.friendsIds = friendsIds;
    }
}

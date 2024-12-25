package com.androidlesson.domain.main.models;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private String userName,userSurname;
    private List<String> friendsIds,taskToFriendsIds;

    public UserInfo(String userName, String userSurname, List<String> friendsIds, List<String> taskToFriendsIds) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.friendsIds = friendsIds;
        this.taskToFriendsIds = taskToFriendsIds;
    }

    public UserInfo(String userName, String userSurname) {
        this.userName = userName;
        this.userSurname = userSurname;
        friendsIds=new ArrayList<>();
        taskToFriendsIds=new ArrayList<>();
    }

    public UserInfo(UserData user) {
        userName=user.getUserName();
        userSurname=user.getUserSurname();
        friendsIds=user.getFriendsIds();
        taskToFriendsIds=user.getTaskToFriendsIds();
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
}

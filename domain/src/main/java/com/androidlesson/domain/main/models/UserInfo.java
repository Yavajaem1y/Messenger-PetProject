package com.androidlesson.domain.main.models;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private String userName,userSurname,userSystemId;
    private List<String> friendsIds,taskToFriendsIds;

    public UserInfo(String userName, String userSurname, List<String> friendsIds, List<String> taskToFriendsIds) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.friendsIds = friendsIds;
        this.taskToFriendsIds = taskToFriendsIds;
    }

    public UserInfo(String userSystemId,String userName, String userSurname) {
        this.userName = userName;
        this.userSystemId=userSystemId;
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

    public String getUserSystemId() {
        return userSystemId;
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

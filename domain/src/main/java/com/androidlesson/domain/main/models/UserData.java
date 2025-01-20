package com.androidlesson.domain.main.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable{
    private String userId,userName,userSurname;
    private List<String> friendsIds,taskToFriendsIds,subscribersIds,chatIds;

    public UserData() {
    }

    public UserData(String userId, String userName, String userSurname, List<String> friendsIds, List<String> taskToFriendsIds, List<String> subscribersIds, List<String> chatIds) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.friendsIds = friendsIds;
        this.taskToFriendsIds = taskToFriendsIds;
        this.subscribersIds = subscribersIds;
        this.chatIds = chatIds;
    }

    public UserData(String userId, String userSurname, String userName) {
        this.userId = userId;
        this.userSurname = userSurname;
        this.userName = userName;
        friendsIds=new ArrayList<>();
        taskToFriendsIds=new ArrayList<>();
        subscribersIds=new ArrayList<>();
        chatIds=new ArrayList<>();
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

    public List<String> getSubscribersIds() {
        return subscribersIds;
    }

    public List<String> getChatIds() {
        return chatIds;
    }

    public void addToFriend(String id){
        friendsIds.add(id);
    }

    public void removeFriend(String id){
        friendsIds.remove(id);
    }

    public void addTaskToFriend(String id){
        taskToFriendsIds.add(id);
    }

    public void removeTaskToFriend(String id){
        taskToFriendsIds.remove(id);
    }

    public void addSubscriber(String id){
        subscribersIds.add(id);
    }

    public void removeSubscriber(String id){
        subscribersIds.remove(id);
    }
}

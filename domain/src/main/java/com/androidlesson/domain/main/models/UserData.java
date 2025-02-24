package com.androidlesson.domain.main.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable{
    private String userId,userSystemId,userName,userSurname,imageData,userInfo;
    private List<String> friendsIds,taskToFriendsIds,subscribersIds,chatIds;

    public UserData() {
    }

    public UserData(String userId,String userSystemId, String userName, String userSurname, String imageData, List<String> friendsIds, List<String> taskToFriendsIds, List<String> subscribersIds, List<String> chatIds, String userInfo) {
        this.userId = userId;
        this.userSystemId=userSystemId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.imageData=imageData;
        this.friendsIds = (friendsIds == null) ? new ArrayList<>() : friendsIds;
        this.taskToFriendsIds = (taskToFriendsIds == null) ? new ArrayList<>() : taskToFriendsIds;
        this.subscribersIds = (subscribersIds == null) ? new ArrayList<>() : subscribersIds;
        this.chatIds = (chatIds == null) ? new ArrayList<>() : chatIds;
        this.userInfo=userInfo;
    }

    public UserData(String userId,String userSystemId ,String userName, String userSurname, String imageData) {
        this.userId = userId;
        this.userSystemId=userSystemId;
        this.userSurname = userSurname;
        this.userName = userName;
        this.imageData=imageData;
        this.friendsIds = new ArrayList<>();
        this.taskToFriendsIds = new ArrayList<>();
        this.subscribersIds = new ArrayList<>();
        this.chatIds = new ArrayList<>();
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public void setSubscribersIds(List<String> subscribersIds) {
        this.subscribersIds = subscribersIds;
    }

    public void setChatIds(List<String> chatIds) {
        this.chatIds = chatIds;
    }

    public String getUserSystemId() {
        return userSystemId;
    }

    public void setUserSystemId(String userSystemId) {
        this.userSystemId = userSystemId;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}

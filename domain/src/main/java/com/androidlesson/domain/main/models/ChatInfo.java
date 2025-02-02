package com.androidlesson.domain.main.models;

import java.util.List;

public class ChatInfo {
    private String chatId,firstUser,secondUser, timeLastMessage;
    private Integer numberOfMessages;
    private List<String> firstUserChatsIds, secondUserChatsIds;

    public ChatInfo() {
    }

    public ChatInfo(String chatId) {
        this.chatId = chatId;
    }

    public ChatInfo(String chatId, String firstUser, String secondUser, String timeLastMessage, Integer numberOfMessages, List<String> firstUserChatsIds, List<String> secondUserChatsIds) {
        this.chatId = chatId;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.timeLastMessage = timeLastMessage;
        this.numberOfMessages = numberOfMessages;
        this.firstUserChatsIds = firstUserChatsIds;
        this.secondUserChatsIds = secondUserChatsIds;
    }

    private class ChatInfoToDB{
        private String firstUser,secondUser, timeLastMessage;
        private Integer numberOfMessages;
        private List<String> firstUserChatsIds, secondUserChatsIds;

        public ChatInfoToDB() {
            this.firstUser=ChatInfo.this.firstUser;
            this.secondUser=ChatInfo.this.secondUser;
            this.timeLastMessage=ChatInfo.this.timeLastMessage;
            this.numberOfMessages=ChatInfo.this.numberOfMessages;
            this.firstUserChatsIds=ChatInfo.this.firstUserChatsIds;
            this.secondUserChatsIds=ChatInfo.this.secondUserChatsIds;
        }

        public String getFirstUser() {
            return firstUser;
        }

        public String getSecondUser() {
            return secondUser;
        }

        public String getTimeLastMessage() {
            return timeLastMessage;
        }

        public Integer getNumberOfMessages() {
            return numberOfMessages;
        }
    }

    public ChatInfoToDB getChatInfoToDB(){
        return new ChatInfoToDB();
    }

    public String getChatId() {
        return chatId;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }

    public String getTimeLastMessage() {
        return timeLastMessage;
    }

    public Integer getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public List<String> getFirstUserChatsIds() {
        return firstUserChatsIds;
    }

    public List<String> getSecondUserChatsIds() {
        return secondUserChatsIds;
    }

    public void addNewChatId(String chatId){
        firstUserChatsIds.add(chatId);
        secondUserChatsIds.add(chatId);
    }

    public void setTimeLastMessage(String timeLastMessage) {
        this.timeLastMessage = timeLastMessage;
    }

    public void setNumberOfMessages(Integer numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }
}

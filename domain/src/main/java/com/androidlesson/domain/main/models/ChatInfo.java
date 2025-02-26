package com.androidlesson.domain.main.models;

import java.util.ArrayList;
import java.util.List;

public class ChatInfo {
    private String chatId,firstUser,secondUser, timeLastMessage;
    private Integer numberOfMessages;
    private List<String> firstUserChatsIds, secondUserChatsIds;
    private List<Message> messages;

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

    public ChatInfo(ChatInfoToDB chatInfoToDB){
        this.chatId = null;
        this.firstUser = chatInfoToDB.firstUser;
        this.secondUser = chatInfoToDB.secondUser;
        this.timeLastMessage = chatInfoToDB.timeLastMessage;
        this.numberOfMessages = chatInfoToDB.getNumberOfMessages();
        this.firstUserChatsIds = new ArrayList<>();
        this.secondUserChatsIds = new ArrayList<>();
        this.messages=chatInfoToDB.messages;
    }

    public static class Message{
        private String sender, message, timeSending,imageUri;

        public Message() {
        }

        public Message(String sender, String message, String timeSending) {
            this.sender = sender;
            this.message = message;
            this.timeSending = timeSending;
        }

        public Message(String sender, String message, String timeSending, String imageUri) {
            this.sender = sender;
            this.message = message;
            this.timeSending = timeSending;
            this.imageUri = imageUri;
        }

        public String getImageUri() {
            return imageUri;
        }

        public void setImageUri(String imageUri) {
            this.imageUri = imageUri;
        }

        public String getSender() {
            return sender;
        }

        public String getMessage() {
            return message;
        }

        public String getTimeSending() {
            return timeSending;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setTimeSending(String timeSending) {
            this.timeSending = timeSending;
        }
    }

    public class ChatInfoToDB{
        private String firstUser,secondUser, timeLastMessage,textLastMessage;
        private Integer numberOfMessages;
        private List<Message> messages;

        public ChatInfoToDB() {
            this.firstUser=ChatInfo.this.firstUser;
            this.secondUser=ChatInfo.this.secondUser;
            this.timeLastMessage=ChatInfo.this.timeLastMessage;
            this.numberOfMessages=ChatInfo.this.numberOfMessages;
            this.messages=ChatInfo.this.messages;
        }

        public String getTextLastMessage() {
            return textLastMessage;
        }

        public void setTextLastMessage(String textLastMessage) {
            this.textLastMessage = textLastMessage;
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

        public void setFirstUser(String firstUser) {
            this.firstUser = firstUser;
        }

        public void setSecondUser(String secondUser) {
            this.secondUser = secondUser;
        }

        public void setTimeLastMessage(String timeLastMessage) {
            this.timeLastMessage = timeLastMessage;
        }

        public void setNumberOfMessages(Integer numberOfMessages) {
            this.numberOfMessages = numberOfMessages;
        }

        public List<Message> getMessages() {
            return messages;
        }

        public void setMessages(List<Message> messages) {
            this.messages = messages;
        }

        public Integer getNumberOfMessages() {
            return numberOfMessages;
        }
    }

    public void pushNewMessage(String time){
        numberOfMessages++;
        timeLastMessage=time;
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

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
    }

    public void setFirstUserChatsIds(List<String> firstUserChatsIds) {
        this.firstUserChatsIds = firstUserChatsIds;
    }

    public void setSecondUserChatsIds(List<String> secondUserChatsIds) {
        this.secondUserChatsIds = secondUserChatsIds;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

package com.androidlesson.domain.main.models;

public class ChatInfoForLoad {
    private String chatId, firstUser, secondUser ,timeLastMessage,textLastMessage, anotherUserNameAndSurname , anotherUserAvatar;
    private Integer numberOfMessages;

    public ChatInfoForLoad(String chatId, String firstUser, String secondUser, String timeLastMessage, String textLastMessage, String anotherUserNameAndSurname, String anotherUserAvatar, Integer numberOfMessages) {
        this.chatId = chatId;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.timeLastMessage = timeLastMessage;
        this.textLastMessage = textLastMessage;
        this.anotherUserNameAndSurname = anotherUserNameAndSurname;
        this.anotherUserAvatar = anotherUserAvatar;
        this.numberOfMessages = numberOfMessages;
    }

    public ChatInfoForLoad() {
    }

    public String getAnotherUserAvatar() {
        return anotherUserAvatar;
    }

    public void setAnotherUserAvatar(String anotherUserAvatar) {
        this.anotherUserAvatar = anotherUserAvatar;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
    }

    public String getTimeLastMessage() {
        return timeLastMessage;
    }

    public void setTimeLastMessage(String timeLastMessage) {
        this.timeLastMessage = timeLastMessage;
    }

    public String getTextLastMessage() {
        return textLastMessage;
    }

    public void setTextLastMessage(String textLastMessage) {
        this.textLastMessage = textLastMessage;
    }

    public Integer getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(Integer numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public String getAnotherUserNameAndSurname() {
        return anotherUserNameAndSurname;
    }

    public void setAnotherUserNameAndSurname(String anotherUserNameAndSurname) {
        this.anotherUserNameAndSurname = anotherUserNameAndSurname;
    }
}

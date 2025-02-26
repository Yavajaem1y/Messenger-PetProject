package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.interfaces.StringCallback;
import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.ImageToDb;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.utils.CurrentTimeAndDate;

public class SendAMessageUseCase {

    private MainFirebaseRepository firebaseRepository;

    public SendAMessageUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(String message,ChatInfo chatInfo, UserData currentUserData,byte[] image) {
        if (message!=null) {
            message = message.trim();
        }
        if (chatInfo != null && currentUserData != null) {
            CurrentTimeAndDate currentTimeAndDate=new CurrentTimeAndDate();
            chatInfo.pushNewMessage(currentTimeAndDate.getCurrentTime());
            if (image!=null){
                String imageName="images/messageImage/"+currentUserData.getUserId()+currentTimeAndDate.getCurrentTimeToId()+".jpg";
                String finalMessage = message;
                firebaseRepository.sendImageMessage(new ImageToDb(imageName, currentUserData.getUserId(), image), new StringCallback() {
                    @Override
                    public void getString(String imageUri) {
                        ChatInfo.Message messageToDb = new ChatInfo.Message(currentUserData.getUserId(), finalMessage, chatInfo.getTimeLastMessage(),imageUri);
                        firebaseRepository.sendAMessageUseCase(messageToDb, chatInfo);
                    }
                });
            }
            else{
                ChatInfo.Message messageToDb = new ChatInfo.Message(currentUserData.getUserId(), message, chatInfo.getTimeLastMessage());
                firebaseRepository.sendAMessageUseCase(messageToDb, chatInfo);
            }
        }
    }
}

package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.models.ImageToDb;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.utils.CurrentTimeAndDate;

public class UploadImageAvatarUseCase {
    private MainFirebaseRepository firebaseRepository;

    public UploadImageAvatarUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(byte[] imageData, UserData currnetUserData){
        if (imageData!=null) {
            String imageId="images/avatar/"+currnetUserData.getUserSystemId()+ new CurrentTimeAndDate().getCurrentTimeToId()+".jpg";
            firebaseRepository.addImageAvatar(new ImageToDb(imageId,currnetUserData.getUserId(),imageData));
        }

    }
}

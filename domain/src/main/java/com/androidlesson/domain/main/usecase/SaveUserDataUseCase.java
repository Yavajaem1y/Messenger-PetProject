package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.callbacks.CallbackError;
import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.models.Error;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserNameAndSurname;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPrefRepository;

public class SaveUserDataUseCase {

    private MainFirebaseRepository firebaseRepository;
    private MainSharedPrefRepository sharedPrefRepository;
    private CallbackError callbackError;
    private CallbackGetUserData callbackGetUserData;

    public SaveUserDataUseCase(MainFirebaseRepository firebaseRepository, MainSharedPrefRepository sharedPrefRepository) {
        this.firebaseRepository = firebaseRepository;
        this.sharedPrefRepository = sharedPrefRepository;
    }

    private CallbackGetUserData callbackGetUserDataFromDB=new CallbackGetUserData() {
        @Override
        public void getUserData(UserData userData) {
            sharedPrefRepository.saveUserData(userData);
            if (callbackGetUserData!=null)
                callbackGetUserData.getUserData(userData);
        }
    };

    public void execute(UserNameAndSurname user, CallbackError callbackError, CallbackGetUserData callbackGetUserData){
        this.callbackGetUserData=callbackGetUserData;
        if (user.getUserName().isEmpty() || user.getUserSurname().isEmpty()){
            callbackError.getError(new Error("The fields should not be empty"));
        }
        firebaseRepository.saveUserData(user,callbackGetUserDataFromDB);
    }
}

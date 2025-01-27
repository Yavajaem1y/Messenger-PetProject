package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPrefRepository;

public class LoadUserDataUseCase {

    private CallbackGetUserData callbackGetUserData;
    private MainFirebaseRepository firebaseRepository;
    private MainSharedPrefRepository sharedPrefRepository;

    private UserData userData;

    public LoadUserDataUseCase(MainFirebaseRepository firebaseRepository, MainSharedPrefRepository sharedPrefRepository) {
        this.firebaseRepository = firebaseRepository;
        this.sharedPrefRepository = sharedPrefRepository;
    }

    private CallbackGetUserData callbackGetUserDataFromDB=new CallbackGetUserData() {
        @Override
        public void getUserData(UserData userDataFromDB) {
            if (userDataFromDB!=null && userData!=userDataFromDB && callbackGetUserData!=null){
                sharedPrefRepository.saveUserData(userDataFromDB);
                callbackGetUserData.getUserData(userDataFromDB);
            }
        }
    };

    public void execute(CallbackGetUserData callbackGetUserData) {
        userData = sharedPrefRepository.getUserData();
        this.callbackGetUserData = callbackGetUserData;

        if (userData == null ||
                userData.getUserId() == null || userData.getUserId().isEmpty() ||
                userData.getUserName() == null || userData.getUserName().isEmpty() ||
                userData.getUserSurname() == null || userData.getUserSurname().isEmpty()) {
            userData = null;
        }

        if (this.callbackGetUserData != null) {
            this.callbackGetUserData.getUserData(userData);
        }

        firebaseRepository.getCurrentUserData(callbackGetUserDataFromDB);
    }

}
package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.interfaces.CallbackCheckAvailableIds;
import com.androidlesson.domain.main.interfaces.CallbackError;
import com.androidlesson.domain.main.interfaces.CallbackGetUserData;
import com.androidlesson.domain.main.interfaces.CallbackWithId;
import com.androidlesson.domain.main.models.Error;
import com.androidlesson.domain.main.models.UserData;
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

    public void execute(UserData user, CallbackError callbackError, CallbackGetUserData callbackGetUserData){
        this.callbackGetUserData=callbackGetUserData;
        String id=user.getUserId();
        if (user.getUserName().isEmpty() || user.getUserSurname().isEmpty()){
            callbackError.getError(new Error("The fields should not be empty"));
        }
        if(!id.isEmpty()){
            boolean checkLetter = false;

            for (char i : id.toCharArray()) {
                if ((i >= 'A' && i <= 'Z') || (i >= 'a' && i <= 'z')) {
                    checkLetter = true;
                } else if (i >= '0' && i <= '9') {
                    if (!checkLetter) {
                        callbackError.getError(new Error("Id must start with a letter, followed by numbers"));
                        return;
                    }
                } else {
                    callbackError.getError(new Error("Id can only contain letters and numbers"));
                    return;
                }
            }
            firebaseRepository.checkAvailableIds(id, new CallbackCheckAvailableIds() {
                @Override
                public void check(boolean res) {
                    if (res) callbackError.getError(new Error("This id is already occupied"));
                    else firebaseRepository.saveUserData(user,callbackGetUserDataFromDB);
                }
            });
        }
        else{
            firebaseRepository.getBasicId(new CallbackWithId() {
                @Override
                public void getId(String id) {
                    user.setUserId(id);
                    firebaseRepository.saveUserData(user,callbackGetUserDataFromDB);
                }
            });
        }
    }
}

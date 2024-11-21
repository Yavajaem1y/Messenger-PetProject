package com.androidlesson.petprojectmessenger.presentation.main.viewModels.setCurrentUserDataFragmentViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.callbacks.CallbackError;
import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.models.Error;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserNameAndSurname;
import com.androidlesson.domain.main.usecase.SaveUserDataUseCase;

public class SetCurrentUserDataFragmentVM extends ViewModel {

    private SaveUserDataUseCase saveUserDataUseCase;

    //Initialization use-cases
    public SetCurrentUserDataFragmentVM(SaveUserDataUseCase saveUserDataUseCase) {
        this.saveUserDataUseCase = saveUserDataUseCase;
    }

    private MutableLiveData<UserData> userDataMutableLiveData=new MutableLiveData<>();
    public LiveData<UserData> getUserDataLiveData(){
        return userDataMutableLiveData;
    }
    private MutableLiveData<Error> ErrorMutableLiveData=new MutableLiveData<>();
    public LiveData<Error> getErrorLiveData(){
        return ErrorMutableLiveData;
    }

    private CallbackGetUserData callbackGetUserData=new CallbackGetUserData() {
        @Override
        public void getUserData(UserData userData) {
            if (userData!=null)
                userDataMutableLiveData.setValue(userData);
        }
    };

    private CallbackError callbackError=new CallbackError() {
        @Override
        public void getError(Error error) {
            ErrorMutableLiveData.setValue(error);
        }
    };

    public void SaveUserData(UserNameAndSurname user){
        saveUserDataUseCase.execute(user,callbackError,callbackGetUserData);
    }
}

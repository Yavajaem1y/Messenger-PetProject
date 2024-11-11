package com.androidlesson.petprojectmessenger.presentation.main.di.mainActivityViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.mainUseCase.SaveCurrentUserDataUseCase;
import com.androidlesson.domain.main.models.FullUserData;
import com.androidlesson.domain.main.models.UserNameAndSurname;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.callbacks.CallbackCurrentUserInfoFromFireBase;
import com.androidlesson.domain.main.repository.callbacks.CallbackSaveUserData;
import com.androidlesson.domain.main.repository.MainSharedPreferencesRepository;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserDataFromDB;

import java.util.Objects;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<Boolean> getCurrentUserDataSceneMutableLiveData=new MutableLiveData<>();

    public LiveData<Boolean> getCurrentUserDataSceneLiveData() {
        return getCurrentUserDataSceneMutableLiveData;
    }

    private MainSharedPreferencesRepository mainSharedPreferencesRepository;
    private MainFirebaseRepository mainFirebaseRepository;
    private SaveCurrentUserDataUseCase saveCurrentUserDataUseCase;
    private CurrentUserDataFromDB currentUserDataFromDB;

    private FullUserData userData=null;

    public MainActivityViewModel(MainSharedPreferencesRepository mainSharedPreferencesRepository,
                                 MainFirebaseRepository mainFirebaseRepository,
                                 SaveCurrentUserDataUseCase saveCurrentUserDataUseCase) {
        this.mainSharedPreferencesRepository = mainSharedPreferencesRepository;
        this.saveCurrentUserDataUseCase=saveCurrentUserDataUseCase;
        this.mainFirebaseRepository=mainFirebaseRepository;

        currentUserDataFromDB=new CurrentUserDataFromDB() {
            @Override
            public void setCurrentUserData(FullUserData userData) {
            }

            @Override
            public FullUserData getCurrentUserData() {
                return userData;
            }
        };

        checkCurrentUserData();
    }

    public CurrentUserDataFromDB getCurrentUserDataFromDB(){
        return currentUserDataFromDB;
    }

    private CallbackSaveUserData callbackSaveUserData=new CallbackSaveUserData() {
        @Override
        public void checkUserDataSave() {
            checkCurrentUserData();
        }
    };

    private CallbackCurrentUserInfoFromFireBase callbackCurrentUserInfoFromFireBase=new CallbackCurrentUserInfoFromFireBase() {
        @Override
        public void callbackUserInfo(FullUserData fullUserData) {
            if (fullUserData ==null) getCurrentUserDataSceneMutableLiveData.postValue(true);
            else {
                userData=fullUserData;
                mainSharedPreferencesRepository.refreshCurrentUserNameAndSurname(new UserNameAndSurname(fullUserData.getUserName(), fullUserData.getUserSurname()));
                getCurrentUserDataSceneMutableLiveData.postValue(false);
            }
        }
    };


    private void checkCurrentUserData(){
        this.userData = mainSharedPreferencesRepository.loadUserData();
        if (Objects.equals(userData.getUserID(), "") || Objects.equals(userData.getUserName(), "") || Objects.equals(userData.getUserSurname(), "")){
            mainFirebaseRepository.getCurrentUserData(callbackCurrentUserInfoFromFireBase);
        }
        else {
            getCurrentUserDataSceneMutableLiveData.postValue(false);
        }
    }

    //For SetCurrentUserDataFragment
    public void saveCurrUserData(FullUserData fullUserData){
        saveCurrentUserDataUseCase.execute(fullUserData,callbackSaveUserData);
        this.userData=fullUserData;
    }



}

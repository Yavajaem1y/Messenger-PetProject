package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.fragmentsViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.mainUseCase.LogOutUseCase;
import com.androidlesson.domain.main.mainUseCase.SetUserProfileAvatarUseCase;
import com.androidlesson.domain.main.models.FullUserData;
import com.androidlesson.domain.main.models.UserNameAndSurname;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.repository.MainSharedPreferencesRepository;
import com.androidlesson.domain.main.repository.callbacks.CallbackCurrentUserInfoFromFireBase;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserAvatarFromMainVM;
import com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks.CurrentUserDataFromDB;

public class CurrentUserProfileViewModel extends ViewModel {
    private CurrentUserAvatarFromMainVM currentUserAvatarFromMainVM;
    private CurrentUserDataFromDB currentUserDataFromDB;

    private MainSharedPreferencesRepository mainSharedPreferencesRepository;
    private MainFirebaseRepository mainFirebaseRepository;
    private SetUserProfileAvatarUseCase setUserProfileAvatarUseCase;
    private LogOutUseCase logOutUseCase;
    public MutableLiveData<UserNameAndSurname> userNameAndSurnameMutableLiveData =new MutableLiveData<>();
    public MutableLiveData<FullUserData> fullUserDataMutableLiveData=new MutableLiveData<>();

    public MutableLiveData<byte[]> userAvatarMutableLiveData=new MutableLiveData<>();

    public CurrentUserProfileViewModel(CurrentUserAvatarFromMainVM currentUserAvatarFromMainVM,
                                       CurrentUserDataFromDB currentUserDataFromDB,
                                       MainSharedPreferencesRepository mainSharedPreferencesRepository,
                                       MainFirebaseRepository mainFirebaseRepository,
                                       SetUserProfileAvatarUseCase setUserProfileAvatarUseCase,
                                       LogOutUseCase logOutUseCase) {
        this.currentUserAvatarFromMainVM = currentUserAvatarFromMainVM;
        this.mainSharedPreferencesRepository = mainSharedPreferencesRepository;
        this.mainFirebaseRepository = mainFirebaseRepository;
        this.setUserProfileAvatarUseCase = setUserProfileAvatarUseCase;
        this.logOutUseCase=logOutUseCase;
        this.currentUserDataFromDB = currentUserDataFromDB;

        checkUserDataFromActivity();
    }

    private void checkUserDataFromActivity(){
        FullUserData currentUserData= currentUserDataFromDB.getCurrentUserData();
        if (currentUserData!=null && !currentUserData.getUserID().isEmpty() &&
                !currentUserData.getUserName().isEmpty() && !currentUserData.getUserSurname().isEmpty()){
            fullUserDataMutableLiveData.setValue(currentUserData);
            userNameAndSurnameMutableLiveData.setValue(new UserNameAndSurname(currentUserData.getUserName(),currentUserData.getUserSurname()));
        }
        else {
            loadUserInfo();
        }
    }

    private final CallbackCurrentUserInfoFromFireBase callbackCurrentUserInfoFromFireBase =new CallbackCurrentUserInfoFromFireBase() {
        @Override
        public void callbackUserInfo(FullUserData fullUserData) {
            currentUserDataFromDB.setCurrentUserData(fullUserData);
            String name= fullUserData.getUserName();
            String surname= fullUserData.getUserSurname();
            if (name.isEmpty() || surname.isEmpty()) return;
            mainSharedPreferencesRepository.refreshCurrentUserNameAndSurname(new UserNameAndSurname(fullUserData.getUserName(), fullUserData.getUserSurname()));
            userNameAndSurnameMutableLiveData.setValue(new UserNameAndSurname(name,surname));
        }
    };

    private void loadUserInfo() {
        UserNameAndSurname userInfoSP = mainSharedPreferencesRepository.getUserNameAndSurname();
        userNameAndSurnameMutableLiveData.setValue(userInfoSP);
        mainFirebaseRepository.getCurrentUserData(callbackCurrentUserInfoFromFireBase);
    }

    public void logOut(){
        logOutUseCase.execute();
    }

    public void setAvatarImage(byte[] image){
        userAvatarMutableLiveData.setValue(image);
        setUserProfileAvatarUseCase.execute(image);
    }


}

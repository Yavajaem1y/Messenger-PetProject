package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainActivityViewModel;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.LoadUserDataUseCase;
import com.androidlesson.domain.main.usecase.LogOutUseCase;
import com.androidlesson.petprojectmessenger.presentation.main.MainFragment;
import com.androidlesson.petprojectmessenger.presentation.main.SetCurrentUserDataFragment;
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackLogOut;
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackUserDataIsSaved;

public class MainActivityViewModel extends ViewModel {

    private LoadUserDataUseCase loadUserDataUseCase;
    private LogOutUseCase logOutUseCase;

    //Initialization use-cases
    public MainActivityViewModel(LoadUserDataUseCase loadUserDataUseCase, LogOutUseCase logOutUseCase) {
        this.loadUserDataUseCase=loadUserDataUseCase;
        this.logOutUseCase=logOutUseCase;

        logOutMutableLiveData.setValue(false);

        loadCurrentUserData();
    }

    private MutableLiveData<Fragment> currentFragmentMutableLiveData=new MutableLiveData<>();
    public LiveData<Fragment> getCurrentFragmentLiveData(){
        return currentFragmentMutableLiveData;
    }

    private MutableLiveData<Boolean> logOutMutableLiveData=new MutableLiveData<>();
    public LiveData<Boolean> logOutLiveData(){
        return logOutMutableLiveData;
    }

    private UserData currentUserData;

    private CallbackLogOut callbackLogOut=new CallbackLogOut() {
        @Override
        public void logout() {
            logOutUseCase.execute();
            logOutMutableLiveData.setValue(true);
        }
    };

    private CallbackUserDataIsSaved callbackUserDataIsSaved=new CallbackUserDataIsSaved() {
        @Override
        public void UserDataIsSaved(UserData userData) {
            currentUserData=userData;
            currentFragmentMutableLiveData.setValue(new MainFragment(currentUserData,callbackLogOut));
        }
    };

    private CallbackGetUserData callbackGetUserData=new CallbackGetUserData() {
        @Override
        public void getUserData(UserData userData) {
            if (userData==null) {
                Log.d("AAA","UserData from db = null");
                currentFragmentMutableLiveData.setValue(new SetCurrentUserDataFragment(callbackUserDataIsSaved));
            }
            else {
                Log.d("AAA","UserData from db = "+userData.getUserId()+" "+userData.getUserName()+" "+userData.getUserSurname());
                currentUserData=userData;
                currentFragmentMutableLiveData.setValue(new MainFragment(currentUserData,callbackLogOut));
            }
        }
    };

    private void loadCurrentUserData(){
        Log.d("AAA","LoadData start");
        loadUserDataUseCase.execute(callbackGetUserData);
    }
}

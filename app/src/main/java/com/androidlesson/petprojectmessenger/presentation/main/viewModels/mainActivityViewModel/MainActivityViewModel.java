package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainActivityViewModel;
import android.os.Bundle;
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
import com.androidlesson.petprojectmessenger.presentation.main.model.SerializableCallbackLogOut;
import com.androidlesson.petprojectmessenger.presentation.main.model.SerializableUserData;

public class MainActivityViewModel extends ViewModel {

    private LoadUserDataUseCase loadUserDataUseCase;
    private LogOutUseCase logOutUseCase;

    private Bundle bundleForMainFragment;

    //Initialization use-cases
    public MainActivityViewModel(LoadUserDataUseCase loadUserDataUseCase, LogOutUseCase logOutUseCase) {
        this.loadUserDataUseCase=loadUserDataUseCase;
        this.logOutUseCase=logOutUseCase;

        bundleForMainFragment=new Bundle();
        bundleForMainFragment.putSerializable("CALLBACK_LOG_OUT",new SerializableCallbackLogOut(callbackLogOut));

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

    public MutableLiveData<Integer> newUserDataHasBeenReceivedMutableLiveData=new MutableLiveData<>(0);
    public LiveData<Integer> newUserDataHasBeenReceivedLiveData(){
        return newUserDataHasBeenReceivedMutableLiveData;
    }

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
            bundleForMainFragment.putSerializable("USERDATA",new SerializableUserData(userData));
            Fragment mainFragment=new MainFragment();
            mainFragment.setArguments(bundleForMainFragment);
            currentFragmentMutableLiveData.setValue(mainFragment);
        }
    };

    private UserData currUserData;

    private final CallbackGetUserData callbackGetUserData=new CallbackGetUserData() {
        @Override
        public void getUserData(UserData userData) {
            if (userData==null) {
                Log.d("AAA","UserData from db = null");
                currentFragmentMutableLiveData.setValue(new SetCurrentUserDataFragment(callbackUserDataIsSaved));
            }
            else {
                if (currUserData==null || !MainActivityViewModel.this.equals(currUserData,userData)){
                    currUserData=userData;
                    bundleForMainFragment.putSerializable("USERDATA",new SerializableUserData(userData));
                    Log.d("AAA","UserData from db = "+userData.getUserId()+" "+userData.getUserName()+" "+userData.getUserSurname());
                    Fragment mainFragment=new MainFragment();
                    mainFragment.setArguments(bundleForMainFragment);
                    currentFragmentMutableLiveData.setValue(mainFragment);
                }
            }
        }
    };

    private void loadCurrentUserData(){
        Log.d("AAA","LoadData start");
        loadUserDataUseCase.execute(callbackGetUserData);
    }

    private boolean equals(UserData first,UserData second){
        return first.getUserName().equals(second.getUserName()) && first.getUserSurname().equals(second.getUserSurname()) && first.getUserId().equals(second.getUserId());
    }
}

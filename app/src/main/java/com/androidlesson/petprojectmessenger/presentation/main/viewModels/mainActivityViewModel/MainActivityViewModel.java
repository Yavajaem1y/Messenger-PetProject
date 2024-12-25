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
import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackUserDataIsSaved;

public class MainActivityViewModel extends ViewModel {

    private LoadUserDataUseCase loadUserDataUseCase;
    private LogOutUseCase logOutUseCase;

    private Bundle bundleForMainFragment;

    //Initialization use-cases
    public MainActivityViewModel(LoadUserDataUseCase loadUserDataUseCase, LogOutUseCase logOutUseCase) {
        this.loadUserDataUseCase=loadUserDataUseCase;
        this.logOutUseCase=logOutUseCase;

        bundleForMainFragment=new Bundle();

        loadCurrentUserData();
    }

    private MutableLiveData<Fragment> currentFragmentMutableLiveData=new MutableLiveData<>();
    public LiveData<Fragment> getCurrentFragmentLiveData(){
        return currentFragmentMutableLiveData;
    }

    public MutableLiveData<Integer> newUserDataHasBeenReceivedMutableLiveData=new MutableLiveData<>(0);
    public LiveData<Integer> newUserDataHasBeenReceivedLiveData(){
        return newUserDataHasBeenReceivedMutableLiveData;
    }

    private CallbackUserDataIsSaved callbackUserDataIsSaved=new CallbackUserDataIsSaved() {
        @Override
        public void UserDataIsSaved(UserData userData) {
            bundleForMainFragment.putSerializable("USERDATA",userData);
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
                    bundleForMainFragment.putSerializable("USERDATA",userData);
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

    public void logOut(){
        logOutUseCase.execute();
    }
}

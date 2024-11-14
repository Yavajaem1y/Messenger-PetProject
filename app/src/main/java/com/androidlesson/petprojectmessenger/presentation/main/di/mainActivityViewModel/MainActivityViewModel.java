package com.androidlesson.petprojectmessenger.presentation.main.di.mainActivityViewModel;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.LoadUserDataUseCase;
import com.androidlesson.petprojectmessenger.presentation.main.MainFragment;
import com.androidlesson.petprojectmessenger.presentation.main.SetCurrentUserDataFragment;

public class MainActivityViewModel extends ViewModel {

    private LoadUserDataUseCase loadUserDataUseCase;

    //Initialization use-cases
    public MainActivityViewModel(LoadUserDataUseCase loadUserDataUseCase) {
        this.loadUserDataUseCase=loadUserDataUseCase;
        loadCurrentUserData();
    }

    private MutableLiveData<Fragment> currentFragmentMutableLiveData=new MutableLiveData<>();
    public LiveData<Fragment> getCurrentFragmentLiveData(){
        return currentFragmentMutableLiveData;
    }

    private UserData currentUserData;

    private CallbackGetUserData callbackGetUserData=new CallbackGetUserData() {
        @Override
        public void getUserData(UserData userData) {
            if (userData==null) {
                Log.d("AAA","UserData from db = null");
                currentFragmentMutableLiveData.setValue(new SetCurrentUserDataFragment());
            }
            else {
                Log.d("AAA","UserData from db = "+userData.getUserId()+" "+userData.getUserName());
                currentUserData=userData;
                currentFragmentMutableLiveData.setValue(new MainFragment(currentUserData));
            }
        }
    };

    private void loadCurrentUserData(){
        Log.d("AAA","LoadData start");
        loadUserDataUseCase.execute(callbackGetUserData);
    }
}

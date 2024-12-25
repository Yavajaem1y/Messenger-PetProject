package com.androidlesson.petprojectmessenger.presentation.main.viewModels.anotherUserProfileActivityViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.AddToFriendsUseCase;

import java.util.List;

public class AnotherUserProfileActivityViewModel extends ViewModel {

    private AddToFriendsUseCase addToFriendsUseCase;

    public AnotherUserProfileActivityViewModel(AddToFriendsUseCase addToFriendsUseCase) {
        this.addToFriendsUseCase=addToFriendsUseCase;
    }

    //userData from activity
    private MutableLiveData<UserData> anotherUserDataMutableLiveData=new MutableLiveData<>();
    public LiveData<UserData> getAnotherUserDataLiveData(){return anotherUserDataMutableLiveData;}

    public void setUserData(UserData userData){
        anotherUserDataMutableLiveData.setValue(userData);
    }

    private MutableLiveData<Boolean> visibilityTopElementMutableLiveData=new MutableLiveData<>(false);
    public LiveData<Boolean> getVisibilityTopElementLiveData(){return visibilityTopElementMutableLiveData;}

    public void setVisibilityTopElement(int scaleY){
        if (scaleY>0 && Boolean.FALSE.equals(visibilityTopElementMutableLiveData.getValue())) visibilityTopElementMutableLiveData.setValue(true);
        else if (scaleY==0 && Boolean.TRUE.equals(visibilityTopElementMutableLiveData.getValue())) visibilityTopElementMutableLiveData.setValue(false);
    }

    public void addToFriend(UserData currentUser){
        UserData user=anotherUserDataMutableLiveData.getValue();
        List<String> c =user.getFriendsIds();
        if (c!=null){
            if (c.isEmpty()) Log.d("friend","c a is empty");
            for (String i : c) {
                Log.d("friend", "friend a - " + i);
            }
        }
        else Log.d("friend","c a == null");
        c =user.getTaskToFriendsIds();
        if (c!=null){
            if (c.isEmpty()) Log.d("friend","c a is empty");
            for (String i:c){
            Log.d("friend","task to friend a - "+i);
        }
        } else Log.d("friend","c a == null");
        addToFriendsUseCase.execute(anotherUserDataMutableLiveData.getValue(),currentUser);
    }
}

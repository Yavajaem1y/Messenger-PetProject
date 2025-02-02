package com.androidlesson.petprojectmessenger.presentation.main.viewModels.anotherUserProfileActivityViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.callbacks.CallbackWithChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.AddToFriendsUseCase;
import com.androidlesson.domain.main.usecase.GoToChatViewUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataByIdUseCase;

import java.util.List;

public class AnotherUserProfileActivityViewModel extends ViewModel {

    private AddToFriendsUseCase addToFriendsUseCase;
    private LoadUserDataByIdUseCase loadUserDataByIdUseCase;
    private GoToChatViewUseCase goToChatViewUseCase;

    private UserData currentUser=null;

    public AnotherUserProfileActivityViewModel(AddToFriendsUseCase addToFriendsUseCase, LoadUserDataByIdUseCase loadUserDataByIdUseCase, GoToChatViewUseCase goToChatViewUseCase) {
        this.addToFriendsUseCase = addToFriendsUseCase;
        this.loadUserDataByIdUseCase = loadUserDataByIdUseCase;
        this.goToChatViewUseCase=goToChatViewUseCase;
    }

    //userData from activity
    private MutableLiveData<UserData> anotherUserDataMutableLiveData=new MutableLiveData<>(null);
    public LiveData<UserData> getAnotherUserDataLiveData(){return anotherUserDataMutableLiveData;}

    private MutableLiveData<Boolean> visibilityMutableLiveData=new MutableLiveData<>(false);
    public LiveData<Boolean> getVisibilityLiveData(){return visibilityMutableLiveData;}

    private MutableLiveData<String> chatIdMutableLiveData=new MutableLiveData<>();
    public LiveData<String> getChatIdLiveData(){return chatIdMutableLiveData;}

    public void loadUserData(UserData userData){
        loadUserDataByIdUseCase.execute(userData.getUserId(), new CallbackGetUserData() {
            @Override
            public void getUserData(UserData userData) {
                if (userData!=null) {
                    anotherUserDataMutableLiveData.setValue(userData);
                    if (currentUser!=null) visibilityMutableLiveData.setValue(true);
                }
            }
        });
    }

    private MutableLiveData<Boolean> visibilityTopElementMutableLiveData=new MutableLiveData<>(false);
    public LiveData<Boolean> getVisibilityTopElementLiveData(){return visibilityTopElementMutableLiveData;}

    public void setVisibilityTopElement(int scaleY){
        if (scaleY>0 && Boolean.FALSE.equals(visibilityTopElementMutableLiveData.getValue())) visibilityTopElementMutableLiveData.setValue(true);
        else if (scaleY==0 && Boolean.TRUE.equals(visibilityTopElementMutableLiveData.getValue())) visibilityTopElementMutableLiveData.setValue(false);
    }

    public void addToFriend() {
        UserData anotherUser = getAnotherUserDataLiveData().getValue();

        if (currentUser == null || anotherUser == null) {
            return;
        }


        if (currentUser.getFriendsIds() == null || anotherUser.getSubscribersIds() == null) {
            return;
        }

        addToFriendsUseCase.execute(currentUser, anotherUser);
    }

    public void sendAMessage(){
        UserData anotherUser = getAnotherUserDataLiveData().getValue();

        if (currentUser == null || anotherUser == null) {
            return;
        }

        if (currentUser.getChatIds()==null || anotherUser.getChatIds()==null){
            return;
        }

        goToChatViewUseCase.execute(currentUser, anotherUser, new CallbackWithChatInfo() {
            @Override
            public void getChatId(String chatId) {
                if (chatId!=null) chatIdMutableLiveData.setValue(chatId);
            }
        });
    }

    public void setCurrentUser(UserData userData){
        currentUser=userData;
        if (anotherUserDataMutableLiveData.getValue()!=null) visibilityMutableLiveData.setValue(true);
    }
}

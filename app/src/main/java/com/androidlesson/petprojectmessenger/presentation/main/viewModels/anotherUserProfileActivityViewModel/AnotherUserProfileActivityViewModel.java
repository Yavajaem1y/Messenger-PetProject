package com.androidlesson.petprojectmessenger.presentation.main.viewModels.anotherUserProfileActivityViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.CallbackGetUserData;
import com.androidlesson.domain.main.interfaces.CallbackWithChatInfo;
import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.AddToFriendsUseCase;
import com.androidlesson.domain.main.usecase.DeleteAFriendUseCase;
import com.androidlesson.domain.main.usecase.GoToChatViewUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataByIdUseCase;

public class AnotherUserProfileActivityViewModel extends ViewModel {

    private AddToFriendsUseCase addToFriendsUseCase;
    private LoadUserDataByIdUseCase loadUserDataByIdUseCase;
    private GoToChatViewUseCase goToChatViewUseCase;
    private DeleteAFriendUseCase deleteAFriendUseCase;

    private UserData currentUser=null;

    public AnotherUserProfileActivityViewModel(AddToFriendsUseCase addToFriendsUseCase, LoadUserDataByIdUseCase loadUserDataByIdUseCase, GoToChatViewUseCase goToChatViewUseCase, DeleteAFriendUseCase deleteAFriendUseCase) {
        this.addToFriendsUseCase = addToFriendsUseCase;
        this.loadUserDataByIdUseCase = loadUserDataByIdUseCase;
        this.goToChatViewUseCase=goToChatViewUseCase;
        this.deleteAFriendUseCase=deleteAFriendUseCase;
    }

    //userData from activity
    private MutableLiveData<UserData> anotherUserDataMutableLiveData=new MutableLiveData<>(null);
    public LiveData<UserData> getAnotherUserDataLiveData(){return anotherUserDataMutableLiveData;}

    private MutableLiveData<Boolean> visibilityMutableLiveData=new MutableLiveData<>(false);
    public LiveData<Boolean> getVisibilityLiveData(){return visibilityMutableLiveData;}

    private MutableLiveData<String> chatIdMutableLiveData=new MutableLiveData<>();
    public LiveData<String> getChatIdLiveData(){return chatIdMutableLiveData;}

    private MutableLiveData<Integer> visibilityButtonSceneMutableLiveData=new MutableLiveData<>(0);
    public LiveData<Integer> getButtonSceneLiveData(){return visibilityButtonSceneMutableLiveData;}

    public void loadUserData(UserData userData){
        loadUserDataByIdUseCase.execute(userData.getUserId(), new CallbackGetUserData() {
            @Override
            public void getUserData(UserData userData) {
                if (userData!=null) {
                    anotherUserDataMutableLiveData.setValue(userData);
                    if (currentUser!=null) {
                        visibilityMutableLiveData.setValue(true);
                        setVisibilityButton(currentUser,userData);
                    }
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

    public void deleteAFriend(){
        UserData anotherUser = getAnotherUserDataLiveData().getValue();

        if (currentUser == null || anotherUser == null) {
            return;
        }


        if (currentUser.getFriendsIds() == null || anotherUser.getSubscribersIds() == null) {
            return;
        }

        deleteAFriendUseCase.execute(currentUser,anotherUser);
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

            @Override
            public void getChatInfo(ChatInfo chatInfo) {

            }

            @Override
            public void getMessage(ChatInfo.Message message) {

            }
        });
    }

    public void setCurrentUser(UserData userData){
        currentUser=userData;
        if (anotherUserDataMutableLiveData.getValue()!=null) {
            visibilityMutableLiveData.setValue(true);
            setVisibilityButton(currentUser,anotherUserDataMutableLiveData.getValue());
        }
    }

    public void setVisibilityButton(UserData currentUser, UserData anotherUser){
        if (currentUser.getFriendsIds().contains(anotherUser.getUserId())){
            visibilityButtonSceneMutableLiveData.setValue(3); //if the users are friends
        }
        else if (currentUser.getSubscribersIds().contains(anotherUser.getUserId())){
            visibilityButtonSceneMutableLiveData.setValue(2); //if another user is subscribed to us
        }
        else if (currentUser.getTaskToFriendsIds().contains(anotherUser.getUserId())){
            visibilityButtonSceneMutableLiveData.setValue(1); //if i am subscribed to another user
        }
        else {
            visibilityButtonSceneMutableLiveData.setValue(0); //if i am want subscribe on user
        }
    }
}

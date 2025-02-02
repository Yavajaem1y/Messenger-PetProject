package com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.callbacks.CallbackWithChatInfo;
import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.LoadAllMessageUseCase;
import com.androidlesson.domain.main.usecase.LoadChatInfoUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataByIdUseCase;
import com.androidlesson.domain.main.usecase.SendAMessageUseCase;

import java.util.Objects;

public class ChatWithUserActivityViewModel extends ViewModel {
    private String chatId;

    private SendAMessageUseCase sendAMessageUseCase;
    private LoadAllMessageUseCase loadAllMessageUseCase;
    private LoadUserDataByIdUseCase loadUserDataByIdUseCase;
    private LoadChatInfoUseCase loadChatInfoUseCase;

    public ChatWithUserActivityViewModel(SendAMessageUseCase sendAMessageUseCase, LoadAllMessageUseCase loadAllMessageUseCase, LoadUserDataByIdUseCase loadUserDataByIdUseCase,LoadChatInfoUseCase loadChatInfoUseCase) {
        this.sendAMessageUseCase = sendAMessageUseCase;
        this.loadAllMessageUseCase = loadAllMessageUseCase;
        this.loadUserDataByIdUseCase = loadUserDataByIdUseCase;
        this.loadChatInfoUseCase=loadChatInfoUseCase;
    }

    private MutableLiveData<ChatInfo> chatInfoMutableLiveData=new MutableLiveData<>();
    public LiveData<ChatInfo> getChatInfoLiveData(){return chatInfoMutableLiveData;}

    private MutableLiveData<UserData> anotherUserMutableLiveData=new MutableLiveData<>();
    public LiveData<UserData> getAnotherUserLiveData(){return anotherUserMutableLiveData;}

    private MutableLiveData<UserData> currentUserMutableLiveData=new MutableLiveData<>();
    public LiveData<UserData> getCurrentUserLiveData(){return currentUserMutableLiveData;}

    public String getChatId(){
        return chatId;
    }

    public void setChatId(String chatId){
        this.chatId=chatId;
        loadChatInfoUseCase.execute(chatId, new CallbackWithChatInfo() {
            @Override
            public void getChatId(String chatId) {

            }

            @Override
            public void getChatInfo(ChatInfo chatInfo) {
                if (chatInfo!=null) {
                    chatInfoMutableLiveData.setValue(chatInfo);
                    if (getCurrentUserLiveData().getValue()!=null && getAnotherUserLiveData().getValue()==null){
                        String anotherUserId= (Objects.equals(chatInfo.getFirstUser(), getCurrentUserLiveData().getValue().getUserId())) ? chatInfo.getSecondUser():chatInfo.getFirstUser();
                        loadUserDataByIdUseCase.execute(anotherUserId, new CallbackGetUserData() {
                            @Override
                            public void getUserData(UserData userData) {
                                if (userData!=null) {
                                    anotherUserMutableLiveData.setValue(userData);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public void setCurrentUser(UserData userData) {
        currentUserMutableLiveData.setValue(userData);
        if (getCurrentUserLiveData().getValue()!=null && getAnotherUserLiveData().getValue()==null){
            String anotherUserId= (Objects.equals(getChatInfoLiveData().getValue().getFirstUser(), getCurrentUserLiveData().getValue().getUserId())) ? getChatInfoLiveData().getValue().getSecondUser():getChatInfoLiveData().getValue().getFirstUser();
            loadUserDataByIdUseCase.execute(anotherUserId, new CallbackGetUserData() {
                @Override
                public void getUserData(UserData userData) {
                    if (userData!=null) {
                        anotherUserMutableLiveData.setValue(userData);
                    }
                }
            });
        }
    }

    public void sendAMessage(String message){
        if (getCurrentUserLiveData().getValue()!=null && getChatInfoLiveData()!=null){
            sendAMessageUseCase.execute(message,getChatInfoLiveData().getValue(),getCurrentUserLiveData().getValue());
        }
    }
}

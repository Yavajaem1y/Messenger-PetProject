package com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.callbacks.CallbackGetUserData;
import com.androidlesson.domain.main.callbacks.CallbackWithChatInfo;
import com.androidlesson.domain.main.models.ChatInfo;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.LoadNewMessageUseCase;
import com.androidlesson.domain.main.usecase.LoadOldMessageUseCase;
import com.androidlesson.domain.main.usecase.LoadChatInfoUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataByIdUseCase;
import com.androidlesson.domain.main.usecase.SendAMessageUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatWithUserActivityViewModel extends ViewModel {
    private String chatId;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private boolean isLoading=false;

    private SendAMessageUseCase sendAMessageUseCase;
    private LoadOldMessageUseCase loadOldMessageUseCase;
    private LoadUserDataByIdUseCase loadUserDataByIdUseCase;
    private LoadChatInfoUseCase loadChatInfoUseCase;
    private LoadNewMessageUseCase loadNewMessageUseCase;

    public ChatWithUserActivityViewModel(LoadNewMessageUseCase loadNewMessageUseCase, LoadChatInfoUseCase loadChatInfoUseCase, LoadUserDataByIdUseCase loadUserDataByIdUseCase, LoadOldMessageUseCase loadOldMessageUseCase, SendAMessageUseCase sendAMessageUseCase) {
        this.loadNewMessageUseCase = loadNewMessageUseCase;
        this.loadChatInfoUseCase = loadChatInfoUseCase;
        this.loadUserDataByIdUseCase = loadUserDataByIdUseCase;
        this.loadOldMessageUseCase = loadOldMessageUseCase;
        this.sendAMessageUseCase = sendAMessageUseCase;
    }

    private final MutableLiveData<ChatInfo> chatInfoMutableLiveData=new MutableLiveData<>();
    public LiveData<ChatInfo> getChatInfoLiveData(){return chatInfoMutableLiveData;}

    private final MutableLiveData<UserData> anotherUserMutableLiveData=new MutableLiveData<>();
    public LiveData<UserData> getAnotherUserLiveData(){return anotherUserMutableLiveData;}

    private final MutableLiveData<UserData> currentUserMutableLiveData=new MutableLiveData<>();
    public LiveData<UserData> getCurrentUserLiveData(){return currentUserMutableLiveData;}

    private final MutableLiveData<List<ChatInfo.Message>> messagesMutableLiveData=new MutableLiveData<>();
    public LiveData<List<ChatInfo.Message>> getMessagesUserLiveData(){return messagesMutableLiveData;}

    private final MutableLiveData<ChatInfo.Message> newMessageMutableLiveData=new MutableLiveData<>();
    public LiveData<ChatInfo.Message> getNewMessagesUserLiveData(){return newMessageMutableLiveData;}

    public String getChatId(){
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;

        loadChatInfoUseCase.execute(chatId, new CallbackWithChatInfo() {
            @Override
            public void getChatId(String chatId) {
            }

            @Override
            public void getChatInfo(ChatInfo chatInfo) {
                Log.d("ChatWithUserActivityViewModel", "ChatInfo loaded: " + (chatInfo != null ? "OK" : "null"));
                if (chatInfo != null) {
                    chatInfoMutableLiveData.setValue(chatInfo);
                    if (getCurrentUserLiveData().getValue() != null && getAnotherUserLiveData().getValue() == null) {
                        String anotherUserId = Objects.equals(chatInfo.getFirstUser(), getCurrentUserLiveData().getValue().getUserId())
                                ? chatInfo.getSecondUser()
                                : chatInfo.getFirstUser();

                        Log.d("ChatWithUserActivityViewModel", "Loading another user data with ID: " + anotherUserId);

                        loadUserDataByIdUseCase.execute(anotherUserId, new CallbackGetUserData() {
                            @Override
                            public void getUserData(UserData userData) {
                                Log.d("ChatWithUserActivityViewModel", "Another user data loaded: " + (userData != null ? userData.getUserId() : "null"));
                                if (userData != null) {
                                    anotherUserMutableLiveData.setValue(userData);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void getMessage(ChatInfo.Message message) {

            }
        });

        loadNewMessage();
    }

    public void setCurrentUser(UserData userData) {
        Log.d("ChatWithUserActivityViewModel", "setCurrentUser() called with: " + (userData != null ? userData.getUserId() : "null"));
        currentUserMutableLiveData.setValue(userData);

        if (getCurrentUserLiveData().getValue() != null && getAnotherUserLiveData().getValue() == null && getChatInfoLiveData().getValue() != null) {
            ChatInfo chatInfo = getChatInfoLiveData().getValue();
            String anotherUserId = Objects.equals(chatInfo.getFirstUser(), getCurrentUserLiveData().getValue().getUserId())
                    ? chatInfo.getSecondUser()
                    : chatInfo.getFirstUser();

            Log.d("ChatWithUserActivityViewModel", "Loading another user data with ID: " + anotherUserId);

            loadUserDataByIdUseCase.execute(anotherUserId, new CallbackGetUserData() {
                @Override
                public void getUserData(UserData userData) {
                    Log.d("ChatWithUserActivityViewModel", "Another user data loaded: " + (userData != null ? userData.getUserId() : "null"));
                    if (userData != null) {
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

    public void loadMessages(String firstMessageTimestamp) {
        isLoading = true;
        disposables.add(loadOldMessageUseCase.execute(firstMessageTimestamp, chatId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messages -> {
                    if (!messages.isEmpty()) {
                        messagesMutableLiveData.setValue(messages);
                    }
                    isLoading = false;
                }, throwable -> {
                    isLoading = false;
                    throwable.printStackTrace();
                }));
    }

    private void loadNewMessage(){
        if (chatId!=null){
            loadNewMessageUseCase.execute(chatId, new CallbackWithChatInfo() {
                @Override
                public void getChatId(String chatId) {

                }

                @Override
                public void getChatInfo(ChatInfo chatInfo) {

                }

                @Override
                public void getMessage(ChatInfo.Message message) {
                    if (message!=null){
                        Log.d("NewMessage",message.getMessage());
                        List <ChatInfo.Message> messages = getMessagesUserLiveData().getValue();
                        if (messages!=null && !messages.contains(message)){
                            messages.add(message);
                            messagesMutableLiveData.setValue(messages);
                            newMessageMutableLiveData.setValue(message);
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}

package com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.CallbackGetUserData;
import com.androidlesson.domain.main.interfaces.CallbackWithChatInfo;
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

    private final MutableLiveData<List<ChatInfo.Message>> messagesMutableLiveData=new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<ChatInfo.Message>> getMessagesUserLiveData(){return messagesMutableLiveData;}

    private final MutableLiveData<byte[]> imageInMessageMutableLiveData=new MutableLiveData<>();
    public LiveData<byte[]> getImageInMessageLiveData(){return imageInMessageMutableLiveData;}

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
                if (chatInfo != null) {
                    chatInfoMutableLiveData.setValue(chatInfo);
                    if (getCurrentUserLiveData().getValue() != null && getAnotherUserLiveData().getValue() == null) {
                        String anotherUserId = Objects.equals(chatInfo.getFirstUser(), getCurrentUserLiveData().getValue().getUserId())
                                ? chatInfo.getSecondUser()
                                : chatInfo.getFirstUser();


                        loadUserDataByIdUseCase.execute(anotherUserId, new CallbackGetUserData() {
                            @Override
                            public void getUserData(UserData userData) {
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
        currentUserMutableLiveData.setValue(userData);

        if (getCurrentUserLiveData().getValue() != null && getAnotherUserLiveData().getValue() == null && getChatInfoLiveData().getValue() != null) {
            ChatInfo chatInfo = getChatInfoLiveData().getValue();
            String anotherUserId = Objects.equals(chatInfo.getFirstUser(), getCurrentUserLiveData().getValue().getUserId())
                    ? chatInfo.getSecondUser()
                    : chatInfo.getFirstUser();


            loadUserDataByIdUseCase.execute(anotherUserId, new CallbackGetUserData() {
                @Override
                public void getUserData(UserData userData) {
                    if (userData != null) {
                        anotherUserMutableLiveData.setValue(userData);
                    }
                }
            });
        }
    }


    public void sendAMessage(String message){
        if (getCurrentUserLiveData().getValue()!=null && getChatInfoLiveData()!=null){
            sendAMessageUseCase.execute(message,getChatInfoLiveData().getValue(),getCurrentUserLiveData().getValue(),imageInMessageMutableLiveData.getValue());
            imageInMessageMutableLiveData.setValue(null);
        }
    }

    public void loadMessages(String firstMessageTimestamp) {
        isLoading = true;
        disposables.add(loadOldMessageUseCase.execute(firstMessageTimestamp, chatId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messages -> {
                    if (!messages.isEmpty()) {
                        List<ChatInfo.Message> outMessages=getMessagesUserLiveData().getValue();
                        for (ChatInfo.Message message:messages){
                            if (!outMessages.contains(message)){
                                outMessages.add(message);
                            }
                        }
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
                    if (message != null) {
                        List<ChatInfo.Message> messages = getMessagesUserLiveData().getValue();
                        if (messages != null) {
                            boolean isDuplicate = false;
                            for (ChatInfo.Message msg : messages) {
                                if (msg.getTimeSending().equals(message.getTimeSending())) {
                                    isDuplicate = true;
                                    break;
                                }
                            }

                            if (!isDuplicate) {
                                Log.d("NewMessage", message.getMessage());
                                messages.add(message);
                                messagesMutableLiveData.setValue(new ArrayList<>(messages));
                            }
                        }
                    }
                }

            });
        }
    }

    public void addImage(byte[] imageData){
        if (imageData!=null){
            imageInMessageMutableLiveData.setValue(imageData);
        }
    }

    public void removeImage(){
        imageInMessageMutableLiveData.setValue(null);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}

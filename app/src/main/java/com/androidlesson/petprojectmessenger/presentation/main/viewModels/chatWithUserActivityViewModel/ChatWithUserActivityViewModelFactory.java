package com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.usecase.LoadNewMessageUseCase;
import com.androidlesson.domain.main.usecase.LoadOldMessageUseCase;
import com.androidlesson.domain.main.usecase.LoadChatInfoUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataByIdUseCase;
import com.androidlesson.domain.main.usecase.SendAMessageUseCase;

public class ChatWithUserActivityViewModelFactory implements ViewModelProvider.Factory {
    private SendAMessageUseCase sendAMessageUseCase;
    private LoadOldMessageUseCase loadOldMessageUseCase;
    private LoadUserDataByIdUseCase loadUserDataByIdUseCase;
    private LoadChatInfoUseCase loadChatInfoUseCase;
    private LoadNewMessageUseCase loadNewMessageUseCase;

    public ChatWithUserActivityViewModelFactory(SendAMessageUseCase sendAMessageUseCase, LoadOldMessageUseCase loadOldMessageUseCase, LoadUserDataByIdUseCase loadUserDataByIdUseCase, LoadChatInfoUseCase loadChatInfoUseCase, LoadNewMessageUseCase loadNewMessageUseCase) {
        this.sendAMessageUseCase = sendAMessageUseCase;
        this.loadOldMessageUseCase = loadOldMessageUseCase;
        this.loadUserDataByIdUseCase = loadUserDataByIdUseCase;
        this.loadChatInfoUseCase = loadChatInfoUseCase;
        this.loadNewMessageUseCase = loadNewMessageUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatWithUserActivityViewModel(loadNewMessageUseCase, loadChatInfoUseCase, loadUserDataByIdUseCase, loadOldMessageUseCase, sendAMessageUseCase);
    }
}

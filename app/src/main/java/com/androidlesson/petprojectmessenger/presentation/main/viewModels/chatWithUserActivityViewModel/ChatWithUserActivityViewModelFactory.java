package com.androidlesson.petprojectmessenger.presentation.main.viewModels.chatWithUserActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.usecase.LoadAllMessageUseCase;
import com.androidlesson.domain.main.usecase.LoadChatInfoUseCase;
import com.androidlesson.domain.main.usecase.LoadUserDataByIdUseCase;
import com.androidlesson.domain.main.usecase.SendAMessageUseCase;

public class ChatWithUserActivityViewModelFactory implements ViewModelProvider.Factory {
    private SendAMessageUseCase sendAMessageUseCase;
    private LoadAllMessageUseCase loadAllMessageUseCase;
    private LoadUserDataByIdUseCase loadUserDataByIdUseCase;
    private LoadChatInfoUseCase loadChatInfoUseCase;

    public ChatWithUserActivityViewModelFactory(SendAMessageUseCase sendAMessageUseCase, LoadAllMessageUseCase loadAllMessageUseCase, LoadUserDataByIdUseCase loadUserDataByIdUseCase, LoadChatInfoUseCase loadChatInfoUseCase) {
        this.sendAMessageUseCase = sendAMessageUseCase;
        this.loadAllMessageUseCase = loadAllMessageUseCase;
        this.loadUserDataByIdUseCase = loadUserDataByIdUseCase;
        this.loadChatInfoUseCase = loadChatInfoUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatWithUserActivityViewModel(sendAMessageUseCase,loadAllMessageUseCase,loadUserDataByIdUseCase,loadChatInfoUseCase);
    }
}

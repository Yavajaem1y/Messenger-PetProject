package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.models.ChatInfoForLoad;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoadAllChatsUseCase {
    private final MainFirebaseRepository firebaseRepository;

    public LoadAllChatsUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public Observable<List<ChatInfoForLoad>> execute(UserData userData) {
        return firebaseRepository.loadChats(userData)
                .subscribeOn(Schedulers.io());
    }
}

package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class LoadAllUserUseCase {

    private MainFirebaseRepository firebaseRepository;

    public LoadAllUserUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public Observable<List<UserData>> execute(String lastKey, int limit) {
        return firebaseRepository.loadAllUser(lastKey, limit);
    }


}

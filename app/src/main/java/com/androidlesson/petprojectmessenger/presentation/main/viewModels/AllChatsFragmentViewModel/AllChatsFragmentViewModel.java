package com.androidlesson.petprojectmessenger.presentation.main.viewModels.AllChatsFragmentViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.models.ChatInfoForLoad;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.LoadAllChatsUseCase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllChatsFragmentViewModel extends ViewModel {
    private final LoadAllChatsUseCase loadAllChatsUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<UserData> currentUserLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ChatInfoForLoad>> chatsLiveData = new MutableLiveData<>();

    public AllChatsFragmentViewModel(LoadAllChatsUseCase loadAllChatsUseCase) {
        this.loadAllChatsUseCase = loadAllChatsUseCase;
    }

    public LiveData<UserData> getCurrentUserLiveData() {
        return currentUserLiveData;
    }

    public void setCurrentUserLiveData(UserData userData) {
        currentUserLiveData.setValue(userData);
        loadChats(userData);
    }

    public LiveData<List<ChatInfoForLoad>> getChatsLiveData() {
        return chatsLiveData;
    }

    private void loadChats(UserData userData) {
        compositeDisposable.add(
                loadAllChatsUseCase.execute(userData)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                chatsLiveData::setValue,
                                throwable -> throwable.printStackTrace()
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}

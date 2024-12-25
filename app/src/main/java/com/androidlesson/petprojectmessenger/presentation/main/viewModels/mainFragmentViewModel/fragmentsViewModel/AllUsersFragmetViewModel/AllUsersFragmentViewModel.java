package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.AllUsersFragmetViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.usecase.LoadAllUserUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllUsersFragmentViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private String lastKey = null;

    private LoadAllUserUseCase loadAllUserUseCase;

    public AllUsersFragmentViewModel(LoadAllUserUseCase loadAllUserUseCase) {
        this.loadAllUserUseCase = loadAllUserUseCase;
    }

    private final MutableLiveData<List<UserData>> usersLiveData = new MutableLiveData<>();
    public LiveData<List<UserData>> getUsersLiveData() {
        return usersLiveData;
    }


    //Load users from db
    public void loadMoreUsers(int limit) {
        disposable.add(
                loadAllUserUseCase.execute(lastKey, limit)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(users -> {
                            if (!users.isEmpty()) {
                                lastKey = users.get(users.size() - 1).getUserId();
                            }
                            List<UserData> currentUsers = usersLiveData.getValue() != null ? usersLiveData.getValue() : new ArrayList<>();
                            currentUsers.addAll(users);
                            usersLiveData.setValue(currentUsers);
                        }, throwable -> {

                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

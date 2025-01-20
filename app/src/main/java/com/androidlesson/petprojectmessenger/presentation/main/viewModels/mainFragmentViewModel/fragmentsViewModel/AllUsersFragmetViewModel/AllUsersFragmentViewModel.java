package com.androidlesson.petprojectmessenger.presentation.main.viewModels.mainFragmentViewModel.fragmentsViewModel.AllUsersFragmetViewModel;

import android.util.Log;

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
    private UserData currUser;
    private boolean filerFriends;

    private List<UserData> allUsers=new ArrayList<>();
    private List<UserData> friends = new ArrayList<>();

    private LoadAllUserUseCase loadAllUserUseCase;

    public AllUsersFragmentViewModel(LoadAllUserUseCase loadAllUserUseCase) {
        this.loadAllUserUseCase = loadAllUserUseCase;
    }

    private final MutableLiveData<List<UserData>> usersLiveData = new MutableLiveData<>();
    public LiveData<List<UserData>> getUsersLiveData() {
        return usersLiveData;
    }

    private void setCurrUser(UserData currUser){
        this.currUser=currUser;
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
                            allUsers.addAll(users);

                            Log.d("AAA",String.valueOf(allUsers.size()));

                            filterFriends(users);
                            filterUsers();

                        }, throwable -> {
                            Log.e("LoadError", "Error loading users", throwable);
                        })
        );
    }

    public void setFilerFriends(boolean bool){
        filerFriends=bool;
    }

    private void filterFriends(List<UserData> newUsers) {
        if (currUser.getFriendsIds() == null || currUser.getFriendsIds().isEmpty()) return;

        for (UserData user : newUsers) {
            if (currUser.getFriendsIds().contains(user.getUserId())) {
                friends.add(user);
                Log.d("friend",user.getUserId());
            }
        }
    }

    private void filterUsers() {
        List<UserData> filteredList = Boolean.TRUE.equals(filerFriends) ? allUsers : friends;
        usersLiveData.setValue(filteredList);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

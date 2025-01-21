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

    private List<UserData> allUsers=new ArrayList<>();
    private List<UserData> friends = new ArrayList<>();

    public LoadAllUserUseCase loadAllUserUseCase;

    public AllUsersFragmentViewModel(LoadAllUserUseCase loadAllUserUseCase) {
        this.loadAllUserUseCase = loadAllUserUseCase;
    }

    private final MutableLiveData<List<UserData>> usersLiveData = new MutableLiveData<>();
    public LiveData<List<UserData>> getUsersLiveData() {
        return usersLiveData;
    }

    public void setCurrUser(UserData userData) {
        this.currUser = userData;
        filterFriends();
    }

    private void filterFriends() {
        friends.clear();
        if (currUser != null && currUser.getFriendsIds() != null) {
            for (UserData user : allUsers) {
                if (currUser.getFriendsIds().contains(user.getUserId())) {
                    friends.add(user);
                }
            }
        }
    }

    //Load users from db
    public void loadMoreUsers(int limit) {
        disposable.add(
                loadAllUserUseCase.execute(lastKey, limit)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(users -> {
                            if (users != null && !users.isEmpty()) {
                                lastKey = users.get(users.size() - 1).getUserId();
                                allUsers.addAll(users);
                                filterFriends(users);
                                filterUsers();
                            } else {
                            }
                        }, throwable -> {
                            Log.e("LoadError", "Error loading users", throwable);
                        })
        );
    }

    private void filterFriends(List<UserData> newUsers) {
        friends.clear();
        if (currUser == null || currUser.getFriendsIds() == null) {
            return;
        }

        for (UserData user : newUsers) {
            if (currUser.getFriendsIds().contains(user.getUserId())) {
                friends.add(user);
            }
        }
    }

    private boolean filterFriends = false;

    public void setFilerFriends(boolean filter) {
        this.filterFriends = filter;
        filterUsers();
    }

    private void filterUsers() {
        List<UserData> filteredList;
        if (filterFriends && currUser != null && currUser.getFriendsIds() != null) {
            filteredList = new ArrayList<>();
            for (UserData user : allUsers) {
                if (currUser.getFriendsIds().contains(user.getUserId())) {
                    filteredList.add(user);
                }
            }
        } else {
            filteredList = new ArrayList<>(allUsers);
        }
        usersLiveData.setValue(filteredList);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

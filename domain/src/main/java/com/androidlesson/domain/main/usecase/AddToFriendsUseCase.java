package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

import java.util.ArrayList;
import java.util.List;

public class AddToFriendsUseCase {
    private MainFirebaseRepository firebaseRepository;

    public AddToFriendsUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(UserData currUser,UserData anotherUser){
        if (currUser!=null && anotherUser!=null){
            if (currUser.getFriendsIds().contains(anotherUser.getUserId()) || anotherUser.getSubscribersIds().contains(currUser.getUserId())){
                return;
            }
            else{
                if (anotherUser.getTaskToFriendsIds().contains(currUser.getUserId())){

                    anotherUser.removeTaskToFriend(currUser.getUserId());
                    anotherUser.addToFriend(currUser.getUserId());

                    currUser.addToFriend(anotherUser.getUserId());
                    currUser.removeSubscriber(anotherUser.getUserId());

                    firebaseRepository.addFriend(currUser,anotherUser);
                }
                else{
                    anotherUser.addSubscriber(currUser.getUserId());
                    currUser.addTaskToFriend(anotherUser.getUserId());

                    firebaseRepository.subscribeOnUser(currUser,anotherUser);
                }
            }
        }
    }
}
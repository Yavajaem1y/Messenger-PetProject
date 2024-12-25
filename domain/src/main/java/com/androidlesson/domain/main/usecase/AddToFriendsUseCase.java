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

    public void execute(UserData anotherUser,UserData currUser){

        List<String> currUserSubscribers=currUser.getTaskToFriendsIds();
        List<String> currUserFriends =currUser.getFriendsIds();

        List<String> anotherUserSubscribers=anotherUser.getTaskToFriendsIds();
        List<String> anotherUserFriends= anotherUser.getFriendsIds();

        if (anotherUserSubscribers.contains(currUser.getUserId()) || currUserFriends.contains(anotherUser.getUserId())) return;

        if (currUserSubscribers!=null && currUserSubscribers.contains(anotherUser.getUserId())){

            currUserSubscribers.remove(anotherUser.getUserId());
            currUser.setTaskToFriendsIds(currUserSubscribers);
            if (currUserFriends==null) currUserFriends=new ArrayList<>();
            currUserFriends.add(anotherUser.getUserId());
            currUser.setFriendsIds(currUserFriends);

            if (anotherUserFriends==null) anotherUserFriends=new ArrayList<>();
            anotherUserFriends.add(currUser.getUserId());
            anotherUser.setFriendsIds(anotherUserFriends);

            firebaseRepository.addFriend(currUser,anotherUser);
        }
        else {
            if(anotherUserSubscribers==null) anotherUserSubscribers=new ArrayList<>();
            anotherUserSubscribers.add(currUser.getUserId());
            anotherUser.setTaskToFriendsIds(anotherUserSubscribers);

            firebaseRepository.subscribeOnUser(anotherUser);
        }
    }
}
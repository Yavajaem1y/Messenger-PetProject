package com.androidlesson.domain.main.usecase;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class DeleteAFriendUseCase {
    private MainFirebaseRepository firebaseRepository;

    public DeleteAFriendUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(UserData currUser, UserData anotherUser){
        if (anotherUser.getFriendsIds().contains(currUser.getUserId())){
            anotherUser.removeFriend(currUser.getUserId());
            currUser.removeFriend(anotherUser.getUserId());

            currUser.addSubscriber(anotherUser.getUserId());
            anotherUser.addTaskToFriend(currUser.getUserId());

            firebaseRepository.removeFriend(currUser,anotherUser);
        }
        else if (anotherUser.getSubscribersIds().contains(currUser.getUserId())){
            anotherUser.removeSubscriber(currUser.getUserId());
            currUser.removeTaskToFriend(anotherUser.getUserId());

            firebaseRepository.unsubscribeFromUser(currUser,anotherUser);
        }
    }
}

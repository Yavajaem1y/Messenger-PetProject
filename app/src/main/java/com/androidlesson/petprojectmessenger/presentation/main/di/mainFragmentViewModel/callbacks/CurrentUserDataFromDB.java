package com.androidlesson.petprojectmessenger.presentation.main.di.mainFragmentViewModel.callbacks;

import com.androidlesson.domain.main.models.FullUserData;

public interface CurrentUserDataFromDB {
    public void setCurrentUserData(FullUserData userData);
    public FullUserData getCurrentUserData();
}

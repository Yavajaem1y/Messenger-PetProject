package com.androidlesson.petprojectmessenger.presentation.main.viewModels.MoreInfoDialogFragmentViewModel;

import androidx.lifecycle.ViewModel;

public class MoreInfoDialogFragmentViewModel extends ViewModel {
    private String info;

    public void setInfo(String info){
        this.info=info;
    }

    public String getInfo(){
        return info;
    }
}

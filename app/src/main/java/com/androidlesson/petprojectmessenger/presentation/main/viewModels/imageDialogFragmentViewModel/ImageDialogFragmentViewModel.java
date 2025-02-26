package com.androidlesson.petprojectmessenger.presentation.main.viewModels.imageDialogFragmentViewModel;

import androidx.lifecycle.ViewModel;

public class ImageDialogFragmentViewModel extends ViewModel {
    private String image;

    public void setImageUri(String image){
        this.image=image;
    }

    public String getImageUri(){
        return image;
    }
}

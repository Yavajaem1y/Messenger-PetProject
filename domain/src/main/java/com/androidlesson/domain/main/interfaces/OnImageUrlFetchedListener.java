package com.androidlesson.domain.main.interfaces;

public interface OnImageUrlFetchedListener {
    void onSuccess(String imageUri);
    void onFailure(Exception e);
}

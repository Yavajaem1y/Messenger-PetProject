package com.androidlesson.petprojectmessenger.presentation.main.model;

import com.androidlesson.petprojectmessenger.presentation.main.callback.CallbackLogOut;

import java.io.Serializable;

public class SerializableCallbackLogOut implements Serializable {

    private CallbackLogOut callbackLogOut;

    public SerializableCallbackLogOut(CallbackLogOut callbackLogOut) {
        this.callbackLogOut = callbackLogOut;
    }

    public CallbackLogOut getCallbackLogOut() {
        return callbackLogOut;
    }
}

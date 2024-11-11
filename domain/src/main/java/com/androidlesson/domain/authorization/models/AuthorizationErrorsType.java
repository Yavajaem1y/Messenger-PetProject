package com.androidlesson.domain.authorization.models;

import java.util.HashMap;

public class AuthorizationErrorsType {
    private HashMap<Integer,String> errorsType;
    public AuthorizationErrorsType() {
        errorsType=new HashMap<>();
        errorsType.put(0,"All fields must be filled in");
        errorsType.put(1,"Passwords must match");
        errorsType.put(2,"The password must be longer than 5");
        errorsType.put(3,"The wrong type of email");
    }

    public String getErrorType(int errorType){
        return errorsType.get(errorType);
    }
}

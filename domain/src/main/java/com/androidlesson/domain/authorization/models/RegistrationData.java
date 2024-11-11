package com.androidlesson.domain.authorization.models;

public class RegistrationData {
    private String email,password,repassword;

    public RegistrationData(String email, String password, String repassword) {
        this.email = email;
        this.password = password;
        this.repassword = repassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepassword() {
        return repassword;
    }
}

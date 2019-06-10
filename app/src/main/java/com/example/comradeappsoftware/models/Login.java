package com.example.comradeappsoftware.models;

public class Login {

    private String email;
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    @Override
    public String toString() {
        return "Login{" +
                "password='" + password + '\'' +
                ", username='" + email + '\'' +
                '}';
    }
}

package com.gutotech.tcclogistica.model;

public class Login {
    private String user;
    private String password;
    private String lastLogin;

    public Login() {
    }

    public Login(String user, String password) {
        this(user, password, "");
    }

    public Login(String user, String password, String lastLogin) {
        this.user = user;
        this.password = password;
        this.lastLogin = lastLogin;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}

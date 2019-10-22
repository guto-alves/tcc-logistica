package com.gutotech.tcclogistica.model;

public class Login {
    private String user;
    private String password;
    private String ultimoLogin;

    public Login() {
    }

    public Login(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public Login(String user, String password, String ultimoLogin) {
        this.user = user;
        this.password = password;
        this.ultimoLogin = ultimoLogin;
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

    public String getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(String ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }
}

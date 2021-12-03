package com.example.demo.session;

import java.util.Arrays;

public class Users {
    private int id;
    private String user;
    private String pwd;
    private AccessType accessType;

    public enum AccessType{
        ADMIN,
        MODERATOR,
        USER,
    }

    public Users() {
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                ", accessType=" + accessType +
                '}';
    }
}

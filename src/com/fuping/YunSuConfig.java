package com.fuping;


public class YunSuConfig
{
    String username;
    String password;
    String softid1;
    String softkey;
    String typeid;
    String timeout;

    public YunSuConfig(String username, String password, String softid1, String softkey, String typeid, String timeout)
    {
        this.username = username;
        this.password = password;
        this.softid1 = softid1;
        this.softkey = softkey;
        this.typeid = typeid;
        this.timeout = timeout;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSoftid1() {
        return this.softid1;
    }

    public void setSoftid1(String softid1) {
        this.softid1 = softid1;
    }

    public String getSoftkey() {
        return this.softkey;
    }

    public void setSoftkey(String softkey) {
        this.softkey = softkey;
    }

    public String getTypeid() {
        return this.typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTimeout() {
        return this.timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
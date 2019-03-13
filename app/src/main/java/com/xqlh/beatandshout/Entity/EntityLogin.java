package com.xqlh.beatandshout.Entity;

/**
 * Created by zl on 2018/11/6.
 */

public class EntityLogin {

    /**
     * UserLoginName : 张三
     * UserLoginPassword : password
     */

    private String UserLoginName;
    private String UserLoginPassword;

    public String getUserLoginName() {
        return UserLoginName;
    }

    public void setUserLoginName(String UserLoginName) {
        this.UserLoginName = UserLoginName;
    }

    public String getUserLoginPassword() {
        return UserLoginPassword;
    }

    public void setUserLoginPassword(String UserLoginPassword) {
        this.UserLoginPassword = UserLoginPassword;
    }
}

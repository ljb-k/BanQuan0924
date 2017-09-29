package com.banquan.ljb.banquan0924;

import cn.bmob.v3.BmobObject;

/**
 * Created by RBL on 2017/9/29.
 */

public class User extends BmobObject{

    private String identity;//身份
    private String account;//账号
    private String password;//密码

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

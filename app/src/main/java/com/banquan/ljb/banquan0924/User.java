package com.banquan.ljb.banquan0924;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by RBL on 2017/9/29.
 */

public class User extends BmobUser{

    private String identity;//身份

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }



}

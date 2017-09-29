package com.banquan.ljb.banquan0924;


public interface RegisterView {
    void onRegisterSuccess();

    void onRegisterFailed();

    void onAccountError();

    void onPasswordError();

    void onStartRegister();
    /**
     * 定义一些注册界面View层的逻辑
     */
}

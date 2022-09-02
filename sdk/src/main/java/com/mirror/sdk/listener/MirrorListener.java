package com.mirror.sdk.listener;

public class MirrorListener {

    public interface LoginListener{
        void onLoginSuccess();
        void onLoginFail();
    };
}

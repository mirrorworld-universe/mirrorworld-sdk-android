package com.mirror.sdk.listener.confirmation;

public interface CheckStatusOfMintingListener {
    void onSuccess(CheckStatusOfMintingResponse response);
    void onCheckFailed(long code, String message);
}


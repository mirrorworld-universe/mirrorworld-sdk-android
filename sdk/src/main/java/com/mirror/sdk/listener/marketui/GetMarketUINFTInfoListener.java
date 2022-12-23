package com.mirror.sdk.listener.marketui;

public interface GetMarketUINFTInfoListener {
    void onSuccess(MirrorMarketUINFTObject object);
    void onFail(long code,String message);
}

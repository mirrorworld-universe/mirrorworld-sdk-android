package com.mirror.sdk.listener.metadata;

public interface GetMarketUINFTInfoListener {
    void onSuccess(MirrorMarketUINFTObject object);
    void onFail(long code,String message);
}

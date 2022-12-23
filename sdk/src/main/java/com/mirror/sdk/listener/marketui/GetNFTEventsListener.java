package com.mirror.sdk.listener.marketui;

import com.mirror.sdk.response.marketui.GetNFTEventsRes;
import com.mirror.sdk.response.marketui.MirrorMarketNFTEvent;

import java.util.List;

public interface GetNFTEventsListener {
    void onSuccess(GetNFTEventsRes result);
    void onFail(long code,String message);
}

package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:24
 * @projectName mirrorworld-sdk-android
 * @className FetchByOwnerListener.java
 * @description TODO
 */
public interface FetchByOwnerListener {
    void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse);
    void onFetchFailed(long code,String message);
}

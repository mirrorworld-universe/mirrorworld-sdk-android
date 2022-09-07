package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:25
 * @projectName mirrorworld-sdk-android
 * @className FetchSingleNFTListener.java
 * @description TODO
 */
public interface FetchSingleNFTListener {
    void onFetchSuccess(UserResponse userResponse);
    void onFetchFailed(long code,String message);
}

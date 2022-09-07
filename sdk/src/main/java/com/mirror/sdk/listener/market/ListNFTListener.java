package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:22
 * @projectName mirrorworld-sdk-android
 * @className ListNFTListener.java
 * @description TODO
 */
public interface ListNFTListener {
    void onListSuccess(UserResponse userResponse);
    void onListFailed(long code,String message);
}

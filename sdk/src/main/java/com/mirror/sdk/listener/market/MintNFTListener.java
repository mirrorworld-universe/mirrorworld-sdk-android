package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:22
 * @projectName mirrorworld-sdk-android
 * @className MintNFTListener.java
 * @description TODO
 */
public interface MintNFTListener {
    void onMintSuccess(UserResponse userResponse);
    void onMintFailed(long code,String message);
}

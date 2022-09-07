package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:26
 * @projectName mirrorworld-sdk-android
 * @className TransferNFTListener.java
 * @description TODO
 */
public interface TransferNFTListener {
    void onTransferSuccess(UserResponse userResponse);
    void onTransferFailed(long code,String message);
}

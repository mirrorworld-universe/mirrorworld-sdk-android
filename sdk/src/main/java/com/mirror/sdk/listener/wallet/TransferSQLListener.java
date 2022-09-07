package com.mirror.sdk.listener.wallet;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:18
 * @projectName mirrorworld-sdk-android
 * @className TransferSQLListener.java
 * @description TODO
 */
public interface TransferSQLListener {
    void onTransferSuccess(UserResponse userResponse);
    void onTransferFailed(long code,String message);
}

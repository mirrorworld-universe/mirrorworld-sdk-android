package com.mirror.sdk.listener.wallet;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:19
 * @projectName mirrorworld-sdk-android
 * @className GetWalletTransactionBySigListener.java
 * @description TODO
 */
public interface GetWalletTransactionBySigListener {
    void onSuccess(UserResponse userResponse);
    void onFailed(long code,String message);
}

package com.mirror.sdk.listener.wallet;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:20
 * @projectName mirrorworld-sdk-android
 * @className GetWalletTokenListener.java
 * @description TODO
 */
public interface GetWalletTokenListener {
    void onSuccess(UserResponse userResponse);
    void onFailed(long code,String message);
}

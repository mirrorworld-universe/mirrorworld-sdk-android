package com.mirror.sdk.listener.wallet;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:20
 * @projectName mirrorworld-sdk-android
 * @className GetWalletTokenListener.java
 * @description TODO
 */
public interface GetWalletTokenListener {
    void onSuccess(GetWalletTokenResponse walletTokenResponse);
    void onFailed(long code,String message);
}

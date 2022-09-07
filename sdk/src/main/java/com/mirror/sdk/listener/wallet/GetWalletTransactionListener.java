package com.mirror.sdk.listener.wallet;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:19
 * @projectName mirrorworld-sdk-android
 * @className GetWalletTransactionListener.java
 * @description TODO
 */
public interface GetWalletTransactionListener {
    void onSuccess(GetWalletTransactionsResponse walletTransactionsResponse);
    void onFailed(long code,String message);
}

package com.mirror.sdk.listener.wallet;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.wallet.TransferResponse;
import com.mirror.sdk.response.wallet.WalletTransaction;

import java.util.List;

/*** @author Pu
 * @createTime 2022/9/7 15:19
 * @projectName mirrorworld-sdk-android
 * @className GetWalletTransactionBySigListener.java
 * @description TODO
 */
public interface GetWalletTransactionBySigListener {
    void onSuccess(List<TransactionsDTO> walletTransactions);
    void onFailed(long code,String message);
}


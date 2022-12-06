package com.mirror.sdk.listener.wallet;

public interface GetOneWalletTransactionBySigListener {
    void onSuccess(TransactionsDTO walletTransactions);

    void onFailed(long code, String message);
}

package com.mirror.sdk.response.wallet;

import com.mirror.sdk.listener.wallet.TransactionsDTO;

import java.util.List;

public class GetWalletTransactionsResponse {
    public int count;
    public String next_before;
    public List<TransactionsDTO> transactions;
}


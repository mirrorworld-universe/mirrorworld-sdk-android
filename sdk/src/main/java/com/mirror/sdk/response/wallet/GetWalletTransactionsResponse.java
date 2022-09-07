package com.mirror.sdk.response.wallet;

import java.util.List;

public class GetWalletTransactionsResponse {
    public int count;
    public String next_before;
    public List<WalletTransaction> transactions;
}


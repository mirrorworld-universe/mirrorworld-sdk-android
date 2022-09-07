package com.mirror.sdk.response.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class WalletTransaction {
    public long blockTime;

    @Expose(deserialize = false)
    @SerializedName("meta")
    public String meta;
    public long slot;
    @Expose(deserialize = false)
    @SerializedName("transaction")
    public String transaction;
}

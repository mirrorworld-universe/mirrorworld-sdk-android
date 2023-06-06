package com.mirror.sdk.evm;

import com.mirror.sdk.solana.MWSolanaAsset;
import com.mirror.sdk.solana.MWSolanaMetadata;
import com.mirror.sdk.solana.MWSolanaWallet;

public class MWEVM {
    public MWEVMAsset Asset;
    public MWEVMWallet Wallet;
    public MWEVMMetadata Metadata;
    public MWEVM(){
        Asset = new MWEVMAsset();
        Wallet = new MWEVMWallet();
        Metadata = new MWEVMMetadata();
    }
}

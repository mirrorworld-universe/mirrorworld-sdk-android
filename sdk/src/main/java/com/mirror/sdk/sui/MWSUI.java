package com.mirror.sdk.sui;

import com.mirror.sdk.evm.MWEVMAsset;
import com.mirror.sdk.evm.MWEVMMetadata;
import com.mirror.sdk.evm.MWEVMWallet;

public class MWSUI {
    public MWSUIAsset Asset;
    public MWSUIWallet Wallet;
    public MWSUI(){
        Asset = new MWSUIAsset();
        Wallet = new MWSUIWallet();
    }
}

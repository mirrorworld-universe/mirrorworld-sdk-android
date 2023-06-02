package com.mirror.sdk.solana;

public class MWSolana {
    public MWSolanaAsset Asset;
    public MWSolanaWallet Wallet;
    public MWSolanaMetadata Metadata;
    public MWSolana(){
        Asset = new MWSolanaAsset();
        Wallet = new MWSolanaWallet();
        Metadata = new MWSolanaMetadata();
    }
}


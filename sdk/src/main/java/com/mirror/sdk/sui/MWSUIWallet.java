package com.mirror.sdk.sui;

import android.app.Activity;

import com.mirror.sdk.chain.MWEVMWrapper;
import com.mirror.sdk.chain.MWSUIWrapper;
import com.mirror.sdk.listener.universal.MirrorCallback;

public class MWSUIWallet {
    //Wallet
    final public void transferToken(String to_publickey, int amount, String token, MirrorCallback mirrorCallback){
        MWSUIWrapper.transferToken(to_publickey, amount, token, mirrorCallback);
    }

    final public void transferSUI(String to_publickey, int amount, MirrorCallback listener){
        MWSUIWrapper.transferSUI(to_publickey, amount, listener);
    }

    final public void getTokens(MirrorCallback walletTokenListener){
        MWSUIWrapper.getTokensOfLoggedUser(walletTokenListener);
    }

    final public void getTransactions(String digest, MirrorCallback walletTransactionListener){
        MWSUIWrapper.getTransactionsOfLoggedUser(digest, walletTransactionListener);
    }
}

package com.mirror.sdk.evm;

import android.app.Activity;

import com.mirror.sdk.chain.MWEVMWrapper;
import com.mirror.sdk.listener.universal.MirrorCallback;

public class MWEVMWallet {
    //Wallet
    //transfer-token
    final public static void transferToken(Activity returnActivity, String nonce, String gasPrice, String gasLimit, String to, int amount, String contract, MirrorCallback mirrorCallback){
        MWEVMWrapper.transferToken(returnActivity, nonce, gasPrice, gasLimit, to, amount, contract, mirrorCallback);
    }

    //transfer-eth
    final public static void transferETH(Activity returnActivity, String nonce, String gasPrice, String gasLimit, String to, int amount, MirrorCallback listener){
        MWEVMWrapper.transferETH(returnActivity, nonce, gasPrice, gasLimit, to, amount, listener);
    }

    final public static void transferBNB(Activity returnActivity, String nonce, String gasPrice, String gasLimit, String to, int amount, MirrorCallback listener){
        MWEVMWrapper.transferBNB(returnActivity, nonce, gasPrice, gasLimit, to, amount, listener);
    }

    final public static void transferMatic(Activity returnActivity, String nonce, String gasPrice, String gasLimit, String to, int amount, MirrorCallback listener){
        MWEVMWrapper.transferMatic(returnActivity, nonce, gasPrice, gasLimit, to, amount, listener);
    }

    final public static void getTokens(MirrorCallback listener){
        MWEVMWrapper.getTokens(listener);
    }

    final public static void getTokensByWallet(String walletAddress, MirrorCallback walletTokenListener){
        MWEVMWrapper.getTokensByWallet(walletAddress, walletTokenListener);
    }

    final public static void getTransactions(int limit, MirrorCallback walletTransactionListener){
        MWEVMWrapper.getTransactionsOfLoggedUser(limit, walletTransactionListener);
    }

    final public static void getTransactionsByWallet(String walletAddress, int limit, MirrorCallback callback){
        MWEVMWrapper.getTransactionsByWallet(walletAddress, limit, callback);
    }

    final public static void getTransactionBySignature(String signature, MirrorCallback listener){
        MWEVMWrapper.getTransactionBySignature(signature, listener);
    }
}

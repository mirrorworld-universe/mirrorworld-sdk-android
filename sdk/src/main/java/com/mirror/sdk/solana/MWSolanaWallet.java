package com.mirror.sdk.solana;

import android.app.Activity;
import android.content.Context;

import com.mirror.sdk.chain.MWSolanaWrapper;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.wallet.GetOneWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransferSOLListener;

public class MWSolanaWallet {

    //Wallet
    final public static void transferToken(Activity returnActivity, String toPublickey, float amount, String token_mint, int decimals, MirrorCallback mirrorCallback){
        MWSolanaWrapper.transferSPLToken(returnActivity, toPublickey, amount, token_mint, decimals, mirrorCallback);
    }

    final public static void transferSOL(Activity returnActivity, String toPublicKey, int amount, TransferSOLListener listener){
        MWSolanaWrapper.transferSOL(returnActivity, toPublicKey, amount, listener);
    }

    final public static void getTokens(GetWalletTokenListener listener){
        MWSolanaWrapper.getTokens(listener);
    }

    final public static void getTokensByWallet(String walletAddress, GetWalletTokenListener walletTokenListener){
        MWSolanaWrapper.getTokensByWallet(walletAddress, walletTokenListener);
    }

    final public static void getTransactions(int limit, String before, GetWalletTransactionListener walletTransactionListener){
        MWSolanaWrapper.getTransactionsOfLoggedUser(limit, before, walletTransactionListener);
    }

    final public static void getTransactionsByWallet(String walletAddress, int limit, String nextBeforeStr, MirrorCallback callback){
        MWSolanaWrapper.getTransactionsByWallet(walletAddress, limit, nextBeforeStr, callback);
    }

    final public static void getTransactionBySignature(String signature, GetOneWalletTransactionBySigListener listener){
        MWSolanaWrapper.getTransactionBySignature(signature, listener);
    }
}

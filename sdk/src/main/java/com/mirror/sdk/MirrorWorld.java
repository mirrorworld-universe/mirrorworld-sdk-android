package com.mirror.sdk;

import android.app.Activity;
import android.util.Log;

import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorSafeOptType;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.particle.MirrorSafeAPI;
import com.mirror.sdk.request.RequestMintNFT;

public class MirrorWorld {

    /**
     * Init SDK
     * @param activity
     * @param mirrorEnv
     */
    final public static void initMirrorWorld(Activity activity, MirrorEnv mirrorEnv){
        MirrorSDK.getInstance().InitSDK(activity,mirrorEnv);
    }

    /**
     * Login
     */
    final public static void startLogin(){
        MirrorSDK.getInstance().StartLogin();
    }

    /**
     * Mint NFT
     * @param collection_mint
     * @param name
     * @param symbol
     * @param detailUrl
     * @param mintNFTListener
     */
    final public static void mintNFT(String collection_mint, String name, String symbol, String detailUrl,MintNFTListener mintNFTListener){
        mintNFT(collection_mint,name,symbol,detailUrl, MirrorConfirmation.Default,mintNFTListener);
    }

    final public static void mintNFT(String collection_mint, String name, String symbol, String detailUrl, String confirmation,MintNFTListener mintNFTListener){
        RequestMintNFT requestMintNFT = new RequestMintNFT();
        requestMintNFT.collection_mint = collection_mint;
        requestMintNFT.name = name;
        requestMintNFT.symbol = symbol;
        requestMintNFT.url = detailUrl;
        requestMintNFT.confirmation = confirmation;

        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.MintNFT, "test", 0,requestMintNFT, new MirrorCallback() {
            @Override
            public void callback(String xAuthToken) {
                Log.d("MirrorSDK world",xAuthToken);
                MirrorSDK.getInstance().SetXAuthToken(xAuthToken);
                MirrorSDK.getInstance().MintNFT(collection_mint,name,symbol,detailUrl,confirmation,mintNFTListener);
            }
        });
    }
}

package com.mirror.sdk.chain;

import static com.mirror.sdk.constant.MirrorUrl.getGetMirrorUrl;

import com.google.gson.reflect.TypeToken;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorResCode;
import com.mirror.sdk.constant.MirrorService;
import com.mirror.sdk.constant.MirrorUrl;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.request.sui.ReqMintNFTAttribute;
import com.mirror.sdk.response.CommonResponse;
import com.mirror.sdk.response.market.SingleNFTResponse;
import com.mirror.sdk.utils.MirrorGsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MWSUIWrapper {
    final public static void getTransactionsOfLoggedUser(String digest, MirrorCallback walletTransactionListener){
        String url = getGetMirrorUrl(MirrorService.Wallet) + MirrorUrl.URL_GET_WALLET_TRANSACTIONS + "/" + digest;
        MirrorSDK.getInstance().checkParamsAndGet(url, null, walletTransactionListener,null);
    }

    final public static void transferSUI(String to_publickey, int amount, MirrorCallback walletTransactionListener){
        String url = getGetMirrorUrl(MirrorService.Wallet) + "transfer-sui";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", to_publickey);
            jsonObject.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().checkParamsAndPost(url, data, walletTransactionListener);
    }

    final public static void transferToken(String to_publickey, int amount, String token, MirrorCallback walletTransactionListener){
        String url = getGetMirrorUrl(MirrorService.Wallet) + "transfer-token";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", to_publickey);
            jsonObject.put("amount", amount);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().checkParamsAndPost(url, data, walletTransactionListener);
    }

    final public static void getTokensOfLoggedUser(MirrorCallback listener){
        String url = getGetMirrorUrl(MirrorService.Wallet) + "tokens";
        MirrorSDK.getInstance().checkParamsAndGet(url, null, listener,null);
    }

    //Asset
    final public static void getMintedCollections(MirrorCallback listener){
        String url = getGetMirrorUrl(MirrorService.AssetMint) + "get-collections";
        MirrorSDK.getInstance().checkParamsAndGet(url, null, listener,null);
    }

    final public static void getNFTOnCollection(String collectionAddress, MirrorCallback listener){
        String url = getGetMirrorUrl(MirrorService.AssetMint) + "get-collection-nfts/" + collectionAddress;
        MirrorSDK.getInstance().checkParamsAndGet(url, null, listener,null);
    }

    final public static void mintCollection(String name, String symbol, String description,String[] creators, MirrorCallback listener){
        String url = getGetMirrorUrl(MirrorService.AssetMint) + "collection";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("symbol", symbol);
            jsonObject.put("description", description);
            jsonObject.put("creators", creators);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().checkParamsAndPost(url, data, listener);
    }

    final public static void mintNFT(String collection_address, String name, String description, String image_url, ReqMintNFTAttribute[] attributes, String to_wallet_address, MirrorCallback listener){
        String url = getGetMirrorUrl(MirrorService.AssetMint) + "nft";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("collection_address", collection_address);
            jsonObject.put("name", name);
            jsonObject.put("description", description);
            jsonObject.put("image_url", image_url);
            jsonObject.put("attributes", attributes);
            jsonObject.put("to_wallet_address", to_wallet_address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().checkParamsAndPost(url, data, listener);
    }
}

package com.mirror.sdk.chain;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorResCode;
import com.mirror.sdk.constant.MirrorSafeOptType;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingListener;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingResponse;
import com.mirror.sdk.listener.market.BuyNFTListener;
import com.mirror.sdk.listener.market.CancelListListener;
import com.mirror.sdk.listener.market.CreateTopCollectionListener;
import com.mirror.sdk.listener.market.FetchByOwnerListener;
import com.mirror.sdk.listener.market.FetchNFTsListener;
import com.mirror.sdk.listener.market.FetchSingleNFTActivityListener;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.TransferNFTListener;
import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionSummaryListener;
import com.mirror.sdk.listener.metadata.GetNFTEventsListener;
import com.mirror.sdk.listener.metadata.GetNFTsListener;
import com.mirror.sdk.listener.metadata.SOLSearchNFTsListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.particle.MirrorSafeAPI;
import com.mirror.sdk.response.CommonResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;
import com.mirror.sdk.response.metadata.MirrorMarketSearchNFTObj;
import com.mirror.sdk.utils.MirrorGsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class MWEVMWrapper extends MWBaseWrapper{
    //SDK
    public final static void initSDK(Activity activityContext, MirrorEnv env){
        MirrorChains chain = MirrorChains.EVM;
        MirrorSDK.getInstance().InitSDK(activityContext,env,chain);
    }

    //Wallet
    //transfer-token
    final public static void transferSPLToken(String nonce, String gasPrice, String gasLimit, String to, int amount, String contract, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nonce", nonce);
            jsonObject.put("gasPrice", gasPrice);
            jsonObject.put("gasLimit", gasLimit);
            jsonObject.put("to", to);
            jsonObject.put("amount", amount);
            jsonObject.put("contract", contract);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.TransferSPLToken, "TransferSPLToken", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().transferToken(data, mirrorCallback);
            }
        });
    }

    //transfer-eth
    final public static void transferETH(String nonce, String gasPrice, String gasLimit, String to, int amount, TransferSOLListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nonce", nonce);
            jsonObject.put("gasPrice", gasPrice);
            jsonObject.put("gasLimit", gasLimit);
            jsonObject.put("to", to);
            jsonObject.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.TransferSol, "TransferSol", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().transferSOL(data, listener);
            }
        });
    }

    final public static void getTokens(MirrorCallback listener){
        MirrorSDK.getInstance().getWalletTokens(listener);
    }

    final public static void getTokensByWallet(String walletAddress, MirrorCallback walletTokenListener){
        MirrorSDK.getInstance().GetWalletTokensByWallet(walletAddress, walletTokenListener);
    }

    final public static void getTransactionsOfLoggedUser(int limit, String before, MirrorCallback walletTransactionListener){
        HashMap<String,String> map = new HashMap<String,String>();
        if(limit != 0) map.put("limit", String.valueOf(limit));
        MirrorSDK.getInstance().transactions(map, walletTransactionListener);
    }

    final public static void getTransactionsByWallet(String walletAddress, int limit, MirrorCallback callback){
        HashMap<String,String> map = new HashMap<String,String>();
        if(limit != 0) map.put("limit", String.valueOf(limit));
        MirrorSDK.getInstance().getTransactionsByWalletOnSolana(walletAddress,map,callback);
    }

    final public static void getTransactionBySignature(String signature, MirrorCallback listener){
        MirrorSDK.getInstance().getTransactionBySignatureOnSolana(signature, listener);
    }

    //Metadata
    final public static void getCollectionInfo(List<String> collections, GetCollectionInfoListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().getCollectionInfo(data, listener);
    }

    final public static void getCollectionFilterInfo(String collectionAddress, GetCollectionFilterInfoListener listener){
        MirrorSDK.getInstance().getCollectionFilterInfo(collectionAddress, listener);
    }

    final public static void getCollectionSummary(List<String> collections, GetCollectionSummaryListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().getCollectionsSummary(data, listener);
    }

    final public static void getNFTInfo(String contractAddress, String tokenID , MirrorCallback listener){
        MirrorSDK.getInstance().GetNFTInfoOnEVM(contractAddress,tokenID,listener);
    }

    final public static void getNFTsByUnabridgedParams(String collection, int page, int page_size, String order_by, boolean desc, double sale, List<JSONObject> filter, MirrorCallback listener){
        MirrorSDK.getInstance().getNFTsByUnabridgedParamsOnEVM(collection, page, page_size, order_by, desc, sale, filter, listener);
    }

    final public static void getNFTEvents(String mint_address, int page, int page_size, MirrorCallback listener){
        MirrorSDK.getInstance().GetNFTEventsOnEVM(mint_address, page, page_size, listener);
    }

    final public static void searchNFTs(List<String> collections, String searchStr, MirrorCallback listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
            jsonObject.put("search", searchStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().searchNFTs(data, listener);
    }

    final public static void recommendSearchNFT(List<String> collections, MirrorCallback listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().RecommendSearchNFT(data, listener);
    }

    //Asset/NFT
    final public static void fetchNFTsByOwnerAddresses(List<String> owners, int limit, MirrorCallback fetchByOwnerListener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : owners) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("owners", jsonArray);
            if(limit != 0) jsonObject.put("limit", limit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        MirrorSDK.getInstance().fetchNFTsByOwnerAddresses(data,fetchByOwnerListener);
    }

    final public static void fetchNFTsByMintAddresses(List<String> tokens, MirrorCallback fetchByMintAddressListener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : tokens) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("tokens", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().fetchNFTsByMintAddresses(data, fetchByMintAddressListener);
    }

    final public static void fetchNFTsByCreatorAddresses(List<String> creators, int limit, int offset, MirrorCallback listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : creators) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("creators", jsonArray);
            jsonObject.put("limit", limit);
            jsonObject.put("offset", offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String  data = jsonObject.toString();
        MirrorSDK.getInstance().FetchNFTsByCreatorAddresses(data, listener);
    }

    final public static void fetchNFTsByUpdateAuthorities(List<String> update_authorities, int limit, int offset, MirrorCallback listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : update_authorities) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("update_authorities", jsonArray);
            if(limit != 0) jsonObject.put("limit", limit);
            if(offset != 0) jsonObject.put("offset", offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().FetchNFTsByUpdateAuthorities(data, listener);
    }

    final public static void fetchNFTMarketplaceActivity(String mint_address, MirrorCallback fetchSingleNFTActivityListener){
        MirrorSDK.getInstance().fetchNFTMarketplaceActivity(mint_address, fetchSingleNFTActivityListener);
    }

    final public static void createVerifiedCollection(String name, String symbol, String detailUrl,String confirmation, MirrorCallback createTopCollectionListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contract_type", symbol);
            jsonObject.put("url", detailUrl);
            jsonObject.put("confirmation", confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.CreateCollection, "CreateCollection", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().CreateVerifiedCollection(data, createTopCollectionListener);
            }
        });
    }

    final public static void transferNFT(String token_id,String collection_address, String to_wallet_address, MirrorCallback transferNFTListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("collection_address", collection_address);
            jsonObject.put("token_id", token_id);
            jsonObject.put("to_wallet_address", to_wallet_address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.TransferNFT, "TransferNFT", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().TransferNFTToAnotherSolanaWallet(data, transferNFTListener);
            }
        });
    }

    final public static void listNFT(String collection_address, String token_id, float price, String auction_house, MirrorCallback listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("collection_address", collection_address);
            jsonObject.put("token_id", token_id);
            jsonObject.put("price",price);
            jsonObject.put("auction_house",auction_house);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.ListNFT, "ListNFT", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().ListNFT(data, listener);
            }
        });
    }

    final public static void cancelNFTListing(String collection_address, String token_id,String marketplace_address, MirrorCallback listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("collection_address", collection_address);
            jsonObject.put("token_id", token_id);
            jsonObject.put("marketplace_address",marketplace_address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.CancelListing, "CancelListing", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().CancelNFTListing(data, listener);
            }
        });
    }

    final public static void buyNFT(String collection_address, String token_id,float price,String marketplace_address, MirrorCallback buyNFTListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("collection_address", collection_address);
            jsonObject.put("token_id", token_id);
            jsonObject.put("price", price);
            jsonObject.put("marketplace_address", marketplace_address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.BuyNFT, "BuyNFT", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().BuyNFT(data, buyNFTListener);
            }
        });
    }
}

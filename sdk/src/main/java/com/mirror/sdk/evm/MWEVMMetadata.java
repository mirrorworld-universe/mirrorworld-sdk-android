package com.mirror.sdk.evm;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.chain.MWEVMWrapper;
import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionSummaryListener;
import com.mirror.sdk.listener.metadata.GetNFTRealPriceListener;
import com.mirror.sdk.listener.universal.MirrorCallback;

import org.json.JSONObject;

import java.util.List;

public class MWEVMMetadata {
    //Metadata
    final public static void getNFTRealPrice(String price, int fee, GetNFTRealPriceListener listener){
        MirrorSDK.getInstance().GetNFTRealPrice(price, fee, listener);
    }

    final public static void getCollectionInfo(List<String> collections, GetCollectionInfoListener listener){
        MWEVMWrapper.getCollectionInfo(collections, listener);
    }

    final public static void getCollectionFilterInfo(String collectionAddress, GetCollectionFilterInfoListener listener){
        MWEVMWrapper.getCollectionFilterInfo(collectionAddress, listener);
    }

    final public static void getCollectionSummary(List<String> collections, GetCollectionSummaryListener listener){
        MWEVMWrapper.getCollectionSummary(collections, listener);
    }

    final public static void getNFTInfo(String contractAddress, int tokenID , MirrorCallback listener){
        MWEVMWrapper.getNFTInfo(contractAddress, tokenID, listener);
    }

    //sale include three status:
    // 0：all 1: for sale 2: not for sale
    final public static void getNFTsByUnabridgedParams(String collection, int page, int page_size, String order_by, boolean desc, int sale, List<JSONObject> filter, MirrorCallback listener){
        MWEVMWrapper.getNFTsByUnabridgedParams(collection, page, page_size, order_by, desc, sale, filter, listener);
    }

    /**
     * Type: Metadata
     * Function: Get NFT events with a NFT mint address.
     */
    final public static void getNFTEvents(String contract,int tokenID, int page, int page_size, MirrorCallback listener){
        MWEVMWrapper.getNFTEvents(contract,tokenID, page, page_size, listener);
    }

    final public static void searchNFTs(List<String> collections, String searchStr, MirrorCallback listener){
        MWEVMWrapper.searchNFTs(collections, searchStr, listener);
    }

    final public static void recommendSearchNFT(List<String> collections, MirrorCallback listener){
        MWEVMWrapper.recommendSearchNFT(collections, listener);
    }
}

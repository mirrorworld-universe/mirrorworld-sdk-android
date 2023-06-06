package com.mirror.sdk.solana;

import com.mirror.sdk.chain.MWSolanaWrapper;
import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionSummaryListener;
import com.mirror.sdk.listener.metadata.GetNFTEventsListener;
import com.mirror.sdk.listener.metadata.GetNFTRealPriceListener;
import com.mirror.sdk.listener.metadata.GetNFTsListener;
import com.mirror.sdk.listener.metadata.SOLSearchNFTsListener;
import com.mirror.sdk.listener.universal.MirrorCallback;

import org.json.JSONObject;

import java.util.List;

public class MWSolanaMetadata {

    //Metadata
    final public static void getNFTRealPrice(String price, int fee, GetNFTRealPriceListener listener){
        MWSolanaWrapper.getNFTRealPrice(price, fee, listener);
    }

    final public static void getCollectionInfo(List<String> collections, GetCollectionInfoListener listener){
        MWSolanaWrapper.getCollectionInfo(collections, listener);
    }

    final public static void getCollectionFilterInfo(String collectionAddress, GetCollectionFilterInfoListener listener){
        MWSolanaWrapper.getCollectionFilterInfo(collectionAddress, listener);
    }

    final public static void getCollectionSummary(List<String> collections, GetCollectionSummaryListener listener){
        MWSolanaWrapper.getCollectionSummary(collections, listener);
    }

    final public static void getNFTInfo(String mintAddress, MirrorCallback listener){
        MWSolanaWrapper.getNFTInfo(mintAddress, listener);
    }

    //sale include three status:
    // 0：all 1: for sale 2: not for sale
    final public static void getNFTsByUnabridgedParams(String collection, int page, int page_size, String order_by, boolean desc, int sale, List<JSONObject> filter, GetNFTsListener listener){
        MWSolanaWrapper.getNFTsByUnabridgedParams(collection, page, page_size, order_by, desc, sale, filter, listener);
    }

    final public static void getNFTEvents(String mint_address, int page, int page_size, GetNFTEventsListener listener){
        MWSolanaWrapper.getNFTEvents(mint_address, page, page_size, listener);
    }

    final public static void searchNFTs(List<String> collections, String searchStr, SOLSearchNFTsListener listener){
        MWSolanaWrapper.searchNFTs(collections, searchStr, listener);
    }

    final public static void recommendSearchNFT(List<String> collections, SOLSearchNFTsListener listener){
        MWSolanaWrapper.recommendSearchNFT(collections, listener);
    }
}

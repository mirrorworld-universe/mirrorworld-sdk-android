package com.mirror.sdk;

import com.mirror.sdk.listener.marketui.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.marketui.GetCollectionInfoListener;
import com.mirror.sdk.listener.marketui.GetNFTEventsListener;
import com.mirror.sdk.listener.marketui.GetNFTRealPriceListener;
import com.mirror.sdk.listener.marketui.GetNFTsListener;
import com.mirror.sdk.listener.marketui.SearchNFTsListener;
import com.mirror.sdk.listener.universal.MirrorCallback;

import org.json.JSONObject;

import java.util.List;

public class MirrorMarket {
    final public static void getCollectionFilterInfo(String collectionAddress, GetCollectionFilterInfoListener listener){
        MirrorSDK.getInstance().GetCollectionFilterInfo(collectionAddress, listener);
    }

    final public static void getNFTInfo(String mintAddress, MirrorCallback listener){
        MirrorSDK.getInstance().GetNFTInfo(mintAddress, listener);
    }

    final public static void getCollectionInfo(List<String> collections, GetCollectionInfoListener listener){
        MirrorSDK.getInstance().GetCollectionInfo(collections, listener);
    }

    final public static void getNFTEvents(String mint_address, int page, int page_size, GetNFTEventsListener listener){
        MirrorSDK.getInstance().GetNFTEvents(mint_address, page, page_size, listener);
    }

    final public static void searchNFTs(List<String> collections, String searchStr, SearchNFTsListener listener){
        MirrorSDK.getInstance().SearchNFTs(collections, searchStr, listener);
    }

    final public static void recommendSearchNFT(List<String> collections, SearchNFTsListener listener){
        MirrorSDK.getInstance().RecommendSearchNFT(collections, listener);
    }

    final public static void getNFTsByUnabridgedParams(String collection, int page, int page_size, String order_by, boolean desc, double sale, List<JSONObject> filter, GetNFTsListener listener){
        MirrorSDK.getInstance().getNFTsByUnabridgedParams(collection, page, page_size, order_by, desc, sale, filter, listener);
    }

    final public static void getNFTRealPrice(String price, int fee, GetNFTRealPriceListener listener){
        MirrorSDK.getInstance().GetNFTRealPrice(price, fee, listener);
    }
}

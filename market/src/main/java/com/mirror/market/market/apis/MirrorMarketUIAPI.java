package com.mirror.market.market.apis;

public class MirrorMarketUIAPI {

//    private final String marketRoot = "https://api-staging.mirrorworld.fun/v1/marketplace/";
//    private final String MarketGetCollectionUrl = marketRoot + "collections";
//    private final String MarketGetCollectionFiltersUrl = marketRoot + "collection/filter_info?collection=";
//    private final String MarketGetNFTs = marketRoot + "nfts";

//    public void OpenNFTManagePage(String image,String name,String minAddress,Double price){
//        checkSDKInited(new OnCheckSDKUseable() {
//            @Override
//            public void OnChecked() {
//                ManageDialog dialog = new ManageDialog();
//                NFTDetailData uiData = new NFTDetailData();
//                uiData.image = image;
//                uiData.name = name;
//                uiData.mint_address = minAddress;
//                uiData.price = price;
//                dialog.init(mActivity,uiData);
//            }
//
//            @Override
//            public void OnUnUsable() {
//
//            }
//        });
//    }
//
//    public void OpenMarket(List<String> collectionAddresses){
//        checkSDKInited(new OnCheckSDKUseable() {
//            @Override
//            public void OnChecked() {
//                MirrorMarketDialog dialogAddGroup = new MirrorMarketDialog();
//                dialogAddGroup.Init(mActivity,collectionAddresses);
//                dialogAddGroup.show(mActivity.getFragmentManager(), "Add group dialog");
//            }
//
//            @Override
//            public void OnUnUsable() {
//
//            }
//        });
//    }
//
//    public void OpenSellPage(String mintAddress,String image,String name){
//        checkSDKInited(new OnCheckSDKUseable() {
//            @Override
//            public void OnChecked() {
//                SellDialog dialog = new SellDialog();
//                NFTDetailData uiData = new NFTDetailData();
//                uiData.image = image;
//                uiData.name = name;
//                uiData.mint_address = mintAddress;
//                uiData.price = 0.0;
//                dialog.init(mActivity,uiData);
//                dialog.show(mActivity.getFragmentManager(),"sell");
//            }
//
//            @Override
//            public void OnUnUsable() {
//
//            }
//        });
//    }
//
//    public void OpenTransferPage(String mintAddress,String image,String name){
//        checkSDKInited(new OnCheckSDKUseable() {
//            @Override
//            public void OnChecked() {
//                TransferDialog dialog = new TransferDialog();
//                NFTDetailData uiData = new NFTDetailData();
//                uiData.image = image;
//                uiData.name = name;
//                uiData.mint_address = mintAddress;
//                uiData.price = 0.0;
//                dialog.init(mActivity,uiData);
//            }
//
//            @Override
//            public void OnUnUsable() {
//
//            }
//        });
//    }

//    public void getMarketNFTs(GetNFTsRequest request, GetNFTsListener listener){
//        String jsonStr = MirrorGsonUtils.getInstance().toJson(request);
//        String url = MarketGetNFTs;
//        checkParamsAndPost(url,jsonStr,getHandlerCallback(new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//
//                UIResponse<List<NFTDetailData>> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<UIResponse<List<NFTDetailData>>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onSuccess(response.data);
//                }else{
//                    listener.onFailed(response.code,response.message);
//                }
//            }
//        }));
//    }
//
//    public void getMarketFilters(String collectionAddress, GetFilterListener listener){
//        String url = MarketGetCollectionFiltersUrl + collectionAddress;
//        checkParamsAndGet(url, null, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<GetFiltersResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetFiltersResponse>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onSuccess(response.data.filter_info);
//                }else
//                    listener.onFailed(response.code,response.message);
//            }
//        });
//    }
//
//    public void getMarketCollections(List<String> addresses, GetCollectionListener listener){
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        for (String tag : addresses) {
//            jsonArray.put(tag);
//        }
//        try {
//            jsonObject.put("collections", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//
//        String url = MarketGetCollectionUrl;
//        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                UIResponse<List<CollectionInfo>> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<UIResponse<List<CollectionInfo>>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onSuccess(response.data);
//                }else{
//                    listener.onFail(response.code,response.message);
//                }
//            }
//        }));
//    }
    /**
     * Get collection list for UI
     * @param listener
     */
//    public static void GetCollections(List<String> addresses,GetCollectionListener listener){
//        GetCollectionsResponse response = new GetCollectionsResponse();
//        response.collections = new ArrayList<>();
//
//        CollectionInfo info = new CollectionInfo();
//        info.collection = "fake_address";
//        info.collection_name = "collection1";
//        info.collection_orders = getTextCollectionOrders();
//
//        response.collections.add(info);
//
//        listener.onSuccess(response);
//    }

    /**
     * Get filters for market UI
     * @param listener
     */
//    public static void GetFilters(CollectionInfo collectionInfo,GetFilterListener listener){
//        GetFiltersResponse response = new GetFiltersResponse();
//        response.collection = "collection's address";
//        response.filter_info = new ArrayList<>();
//
//        CollectionFilter filter1 = new CollectionFilter();
//        filter1.filter_name = "Color";
//        filter1.filter_type = MarketFilterTypes.ENUM;
//        filter1.filter_value = new ArrayList<>();
//        filter1.filter_value.add("Red");
//        filter1.filter_value.add("Blue");
//        filter1.filter_value.add("White");
//
//        response.filter_info.add(filter1);
//
//        CollectionFilter filter2 = new CollectionFilter();
//        filter2.filter_name = "Rare";
//        filter2.filter_type = MarketFilterTypes.ENUM;
//        filter2.filter_value = new ArrayList<>();
//        filter2.filter_value.add("Common");
//        filter2.filter_value.add("Rare");
//        filter2.filter_value.add("Elite");
//        filter2.filter_value.add("Ledengary");
//        filter2.filter_value.add("Mythical");
//        response.filter_info.add(filter2);
//
//        CollectionFilter filter3 = new CollectionFilter();
//        filter3.filter_name = "Role";
//        filter3.filter_type = MarketFilterTypes.ENUM;
//        filter3.filter_value = new ArrayList<>();
//        filter3.filter_value.add("Samuraimon");
//        filter3.filter_value.add("Zombie");
//        filter3.filter_value.add("Cat Maid");
//        filter3.filter_value.add("Pirate Captain");
//        filter3.filter_value.add("Astronautcal");
//        response.filter_info.add(filter3);
//
//
//        listener.onSuccess(response.filter_info);
//    }

//    public static void GetNFTs(Activity activity,int page, GetNFTsListener listener){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        List<NFTDetailData> infos = new ArrayList<>();
//                        for(int i=0;i<5;i++){
//                            NFTDetailData info = new NFTDetailData();
//                            info.name = "Mirror Jump #"+page;
//                            info.image = "https://storage.mirrorworld.fun/nft/6.png";
//                            info.mint_address = "fake_address";
//                            info.price = 0.2139 + i * 0.01;
//
//                            infos.add(info);
//                        }
//
//                        listener.onSuccess(infos);
//                    }
//                });
//            }
//        }).start();
//    }

//    public static void GetSellSummary(GetSellSummaryListener listener){
//        List<NFTSellSummary> summaries = new ArrayList<>();
//        NFTSellSummary s1 = new NFTSellSummary();
//        s1.name = "Service Fee";
//        s1.value = "4.25%";
//        NFTSellSummary s2 = new NFTSellSummary();
//        s2.name = "Listing/Cancel Fee";
//        s2.value = "Free";
//        summaries.add(s1);
//        summaries.add(s2);
//        listener.OnSuccess(summaries);
//    }

//    private static void GetRandomPNG(){
//        List<String> rares = new ArrayList<>();
//        rares.add("Common");
//        rares.add("Rare");
//        rares.add("Elite");
//        rares.add("Ledengary");
//        rares.add("Mythical");
//
//        List<String> roles = new ArrayList<>();
//        roles.add("Samuraimon");
//        roles.add("Zombie");
//        roles.add("Cat Maid");
//        roles.add("Pirate Captain");
//        roles.add("Astronautcal");
//    }

//    private static List<CollectionOrder> getTextCollectionOrders(){
//        List<CollectionOrder> orders = new ArrayList<>();
//
//        CollectionOrder order1 = new CollectionOrder();
//        order1.desc = CollectionOrderDesc.POSITIVE_SEQUENCE;
//        order1.order_field = "price";
//        order1.order_desc = "Price: Low to High";
//        orders.add(order1);
//
//        CollectionOrder order2 = new CollectionOrder();
//        order2.desc = CollectionOrderDesc.INVERTED_ORDER;
//        order2.order_field = "price";
//        order2.order_desc = "Price: High to Low";
//        orders.add(order2);
//
//        return orders;
//    }
}


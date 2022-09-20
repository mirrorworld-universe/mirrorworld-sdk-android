package com.mirror.sdk.ui.market.apis;

import com.mirror.sdk.ui.market.enums.MarketFilterTypes;
import com.mirror.sdk.ui.market.apis.listeners.GetCollectionListener;
import com.mirror.sdk.ui.market.apis.listeners.GetFilterListener;
import com.mirror.sdk.ui.market.apis.listeners.GetNFTsListener;
import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;
import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;
import com.mirror.sdk.ui.market.apis.responses.CollectionOrder;
import com.mirror.sdk.ui.market.apis.responses.CollectionOrderDesc;
import com.mirror.sdk.ui.market.apis.responses.GetCollectionsResponse;
import com.mirror.sdk.ui.market.apis.responses.GetFiltersResponse;
import com.mirror.sdk.ui.market.model.NFTDetailData;

import java.util.ArrayList;
import java.util.List;

public class MirrorMarketUIAPI {

    /**
     * Get collection list for UI
     * @param listener
     */
    public static void GetCollections(GetCollectionListener listener){
        GetCollectionsResponse response = new GetCollectionsResponse();
        response.collections = new ArrayList<>();

        CollectionInfo info = new CollectionInfo();
        info.collection = "fake_address";
        info.collection_name = "collection1";
        info.collection_orders = getTextCollectionOrders();

        response.collections.add(info);

        listener.onSuccess(response);
    }

    /**
     * Get filters for market UI
     * @param listener
     */
    public static void GetFilters(CollectionInfo collectionInfo,GetFilterListener listener){
        GetFiltersResponse response = new GetFiltersResponse();
        response.collection = "collection's address";
        response.filter_info = new ArrayList<>();

        CollectionFilter filter1 = new CollectionFilter();
        filter1.filter_name = "Color";
        filter1.filter_type = MarketFilterTypes.ENUM;
        filter1.filter_value = new ArrayList<>();
        filter1.filter_value.add("Red");
        filter1.filter_value.add("Blue");
        filter1.filter_value.add("White");

        response.filter_info.add(filter1);

        CollectionFilter filter2 = new CollectionFilter();
        filter2.filter_name = "Rare";
        filter2.filter_type = MarketFilterTypes.ENUM;
        filter2.filter_value = new ArrayList<>();
        filter2.filter_value.add("Common");
        filter2.filter_value.add("Rare");
        filter2.filter_value.add("Elite");
        filter2.filter_value.add("Ledengary");
        filter2.filter_value.add("Mythical");
        response.filter_info.add(filter2);

        CollectionFilter filter3 = new CollectionFilter();
        filter3.filter_name = "Role";
        filter3.filter_type = MarketFilterTypes.ENUM;
        filter3.filter_value = new ArrayList<>();
        filter3.filter_value.add("Samuraimon");
        filter3.filter_value.add("Zombie");
        filter3.filter_value.add("Cat Maid");
        filter3.filter_value.add("Pirate Captain");
        filter3.filter_value.add("Astronautcal");
        response.filter_info.add(filter3);


        listener.onSuccess(response.filter_info);
    }

    public static void GetNFTs(GetNFTsListener listener){
        List<NFTDetailData> infos = new ArrayList<>();
        for(int i=0;i<10;i++){
            NFTDetailData info = new NFTDetailData();
            info.name = "Mirror Jump #1";
            info.image = "https://storage.mirrorworld.fun/nft/6.png";
            info.mint_address = "fake_address";
            info.price = 0.2139 + i * 0.01;

            infos.add(info);
        }

        listener.onSuccess(infos);
    }

    private static void GetRandomPNG(){
        List<String> rares = new ArrayList<>();
        rares.add("Common");
        rares.add("Rare");
        rares.add("Elite");
        rares.add("Ledengary");
        rares.add("Mythical");

        List<String> roles = new ArrayList<>();
        roles.add("Samuraimon");
        roles.add("Zombie");
        roles.add("Cat Maid");
        roles.add("Pirate Captain");
        roles.add("Astronautcal");
    }

    private static List<CollectionOrder> getTextCollectionOrders(){
        List<CollectionOrder> orders = new ArrayList<>();

        CollectionOrder order1 = new CollectionOrder();
        order1.desc = CollectionOrderDesc.POSITIVE_SEQUENCE;
        order1.order_field = "price";
        order1.order_desc = "Price: Low to High";
        orders.add(order1);

        CollectionOrder order2 = new CollectionOrder();
        order2.desc = CollectionOrderDesc.INVERTED_ORDER;
        order2.order_field = "price";
        order2.order_desc = "Price: High to Low";
        orders.add(order2);

        return orders;
    }
}


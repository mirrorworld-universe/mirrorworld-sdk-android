//package com.mirror.market.market;
//
//import com.mirror.sdk.ui.market.apis.requests.GetNFTsRequest;
//import com.mirror.sdk.ui.market.apis.requests.GetNFTsRequestFilter;
//import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;
//import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;
//import com.mirror.sdk.ui.market.apis.responses.CollectionOrder;
//import com.mirror.sdk.ui.market.enums.MarketFilterTypes;
//import com.mirror.sdk.ui.market.model.NFTDetailData;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MarketDataController {
//    private static volatile MarketDataController instance;
//    public static MarketDataController getInstance(){
//        if (instance == null){
//            synchronized(MarketDataController.class){
//                instance = new MarketDataController();
//            }
//        }
//        return instance;
//    }
//
//    public int NFTNowPage = 1;
//
//    private MarketDataController(){
//        mNFTs = new ArrayList<>();
//        NFTRequestInfo = new GetNFTsRequest();
//        NFTRequestInfo.filter = new ArrayList<>();
//    }
//
//    //base data
//    private List<CollectionInfo> mCollections;
//    private List<NFTDetailData> mNFTs;
//    //filter data
//    private CollectionOrder mNowOrder;
//    public String NowCollection;
//
//    public void setCollections(List<CollectionInfo> collections){
//        mCollections = collections;
//    }
//
//    public void addNFTs(List<NFTDetailData> nfts){
//        mNFTs.addAll(nfts);
//    }
//    public List<NFTDetailData> getNFTs(){ return mNFTs; }
//
//    public void setOrder(CollectionOrder order){
//        mNowOrder = order;
//    }
//
//    public CollectionOrder getOrder(){
//        return mNowOrder;
//    }
//
//    public GetNFTsRequest NFTRequestInfo;
//    public void addRequestFilterValue(CollectionFilter filter,String value){
////        if(filter.filter_type == MarketFilterTypes.ENUM){
////            addRequestEnumFilter(value);
////        }else if(filter.filter_type == MarketFilterTypes.RANGE){
////            addRequestRangeFilter(value);
////        }else {
////
////        }
//    }
//    public void addRequestRangeFilter(int from,int to){
//        if(NFTRequestInfo.filter == null){
//            NFTRequestInfo.filter = new ArrayList<>();
//        }
//
//        GetNFTsRequestFilter filter = getFilter(MarketFilterTypes.RANGE);
//        if(filter != null){
//            filter.filter_range = new ArrayList<>();
//            filter.filter_range.add(from);
//            filter.filter_range.add(to);
//        }else {
//            GetNFTsRequestFilter newFilter = new GetNFTsRequestFilter();
//            newFilter.filter_type = MarketFilterTypes.RANGE;
//            newFilter.filter_name = "";
//            newFilter.filter_range = new ArrayList<>();
//
//            filter.filter_range = new ArrayList<>();
//            filter.filter_range.add(from);
//            filter.filter_range.add(to);
//        }
//    }
//    public void addRequestEnumFilter(String enumType){
//        if(NFTRequestInfo.filter == null){
//            NFTRequestInfo.filter = new ArrayList<>();
//        }
//
//        GetNFTsRequestFilter filter = getFilter(MarketFilterTypes.ENUM);
//        if(filter != null){
//            if(filter.filter_value == null){
//                filter.filter_value = new ArrayList<>();
//            }
//            if(filter.filter_value.indexOf(enumType) > -1){
//                return;
//            }
//            filter.filter_value.add(enumType);
//        }else {
//            GetNFTsRequestFilter newFilter = new GetNFTsRequestFilter();
//            newFilter.filter_type = MarketFilterTypes.ENUM;
//            newFilter.filter_name = "";
//            newFilter.filter_value = new ArrayList<>();
//            newFilter.filter_value.add(enumType);
//
//            NFTRequestInfo.filter.add(newFilter);
//        }
//    }
//    private GetNFTsRequestFilter getFilter(String enumType){
//        for(int i=0;i<NFTRequestInfo.filter.size();i++){
//            GetNFTsRequestFilter filter = NFTRequestInfo.filter.get(i);
//            if(filter.filter_type == enumType){
//                return filter;
//            }
//        }
//        return null;
//    }
//}

package com.mirror.sdk.ui.market;

import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;
import com.mirror.sdk.utils.MirrorGsonUtils;

public class MarketUIController {
    private static volatile MarketUIController instance;
    public static MarketUIController getInstance(){
        if (instance == null){
            synchronized(MarketUIController.class){
                instance = new MarketUIController();
            }
        }
        return instance;
    }

    //scroll collection tabs
    private RecyclerView mMainParent;
    public void setMainParent(RecyclerView view){
        mMainParent = view;
    }
    public void mainCollectionTabScrollTo(int position){
        if(mMainParent == null) return;
        mMainParent.scrollToPosition(position);
    }

    //
    private CollectionInfo mCurCollection;
    public void selectCollection(CollectionInfo collectionInfo){
        mCurCollection = collectionInfo;
    }
}

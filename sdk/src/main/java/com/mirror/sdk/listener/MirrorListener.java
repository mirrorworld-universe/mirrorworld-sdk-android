package com.mirror.sdk.listener;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.NFTObject;

public class MirrorListener {

    public interface LoginListener{
        void onLoginSuccess();
        void onLoginFail();
    }

    public interface FetchUserListener{
        void onUserFetched(UserResponse userResponse);
        void onFetchFailed(long code,String message);
    }

    public interface FetchSingleNFT{
        void onNFTFetched(NFTObject nftObject);
        void onNFTFetchFailed(long code,String message);
    }
}

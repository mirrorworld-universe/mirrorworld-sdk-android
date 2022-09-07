package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ListingResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:22
 * @projectName mirrorworld-sdk-android
 * @className ListNFTListener.java
 * @description TODO
 */
public interface ListNFTListener {
    void onListSuccess(ListingResponse listingResponse);
    void onListFailed(long code,String message);
}

package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ListingResponse;

/*** @author Pu
 * @createTime 2022/9/7 16:58
 * @projectName mirrorworld-sdk-android
 * @className BuyNFTListener.java
 * @description TODO
 */
public interface BuyNFTListener {
    void onBuySuccess(ListingResponse listingResponse);
    void onBuyFailed(long code,String message);
}

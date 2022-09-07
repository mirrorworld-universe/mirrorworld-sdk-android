package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ListingResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:26
 * @projectName mirrorworld-sdk-android
 * @className TransferNFTListener.java
 * @description TODO
 */
public interface TransferNFTListener {
    void onTransferSuccess(ListingResponse listingResponse);
    void onTransferFailed(long code,String message);
}

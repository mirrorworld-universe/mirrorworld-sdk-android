package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ListingResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:23
 * @projectName mirrorworld-sdk-android
 * @className CancelListListener.java
 * @description TODO
 */
public interface CancelListListener {
    void onCancelSuccess(ListingResponse listingResponse);
    void onCancelFailed(long code,String message);
}

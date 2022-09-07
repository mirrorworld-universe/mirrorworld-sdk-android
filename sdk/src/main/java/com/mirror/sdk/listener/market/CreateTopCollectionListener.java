package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:21
 * @projectName mirrorworld-sdk-android
 * @className CreateTopCollectionListener.java
 * @description TODO
 */
public interface CreateTopCollectionListener {
    void onCreateSuccess(UserResponse userResponse);
    void onCreateFailed(long code,String message);
}

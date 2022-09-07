package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:23
 * @projectName mirrorworld-sdk-android
 * @className UpdateListListener.java
 * @description TODO
 */
public interface UpdateListListener {
    void onUpdateSuccess(UserResponse userResponse);
    void onUpdateFailed(long code,String message);
}

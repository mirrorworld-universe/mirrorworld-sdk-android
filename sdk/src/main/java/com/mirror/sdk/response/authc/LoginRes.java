package com.mirror.sdk.response.authc;

import com.google.gson.annotations.SerializedName;

/*** @author Pu
 * @createTime 2022/9/6 23:52
 * @projectName mirrorworld-sdk-android
 * @className LoginRes.java
 * @description TODO
 */
public class LoginRes {
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public DataDTO data;
    @SerializedName("code")
    public Integer code;
    @SerializedName("message")
    public String message;

    public static class DataDTO {
        @SerializedName("access_token")
        public String accessToken;
        @SerializedName("refresh_token")
        public String refreshToken;
        @SerializedName("user")
        public UserDTO user;

        public static class UserDTO {
            @SerializedName("id")
            public Integer id;
            @SerializedName("eth_address")
            public String ethAddress;
            @SerializedName("sol_address")
            public String solAddress;
            @SerializedName("email")
            public String email;
            @SerializedName("email_verified")
            public Boolean emailVerified;
            @SerializedName("username")
            public String username;
            @SerializedName("wallet")
            public WalletDTO wallet;
            @SerializedName("createdAt")
            public String createdAt;
            @SerializedName("updatedAt")
            public String updatedAt;

            public static class WalletDTO {
                @SerializedName("eth_address")
                public String ethAddress;
                @SerializedName("sol_address")
                public String solAddress;
            }
        }
    }
}

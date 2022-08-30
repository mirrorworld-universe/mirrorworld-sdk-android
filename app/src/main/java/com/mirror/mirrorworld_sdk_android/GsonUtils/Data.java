
package com.mirror.mirrorworld_sdk_android.GsonUtils;


@SuppressWarnings("unused")
public class Data {


    private String accessToken;

    private String password;

    private String refreshToken;

    private User user;

    public String getAccessToken() {
        return accessToken;
    }

    public String getPassword() {
        return password;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public User getUser() {
        return user;
    }

    public static class Builder {

        private String accessToken;
        private String password;
        private String refreshToken;
        private User user;

        public Data.Builder withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Data.Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Data.Builder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Data.Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Data build() {
            Data data = new Data();
            data.accessToken = accessToken;
            data.password = password;
            data.refreshToken = refreshToken;
            data.user = user;
            return data;
        }

    }

}


package com.mirror.mirrorworld_sdk_android.GsonUtils;


@SuppressWarnings("unused")
public class User {


    private String createdAt;

    private String email;

    private Boolean emailVerified;

    private String ethAddress;

    private Long id;

    private String solAddress;

    private String updatedAt;

    private String username;

    private Wallet wallet;

    public String getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public Long getId() {
        return id;
    }

    public String getSolAddress() {
        return solAddress;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public static class Builder {

        private String createdAt;
        private String email;
        private Boolean emailVerified;
        private String ethAddress;
        private Long id;
        private String solAddress;
        private String updatedAt;
        private String username;
        private Wallet wallet;

        public User.Builder withCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public User.Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public User.Builder withEmailVerified(Boolean emailVerified) {
            this.emailVerified = emailVerified;
            return this;
        }

        public User.Builder withEthAddress(String ethAddress) {
            this.ethAddress = ethAddress;
            return this;
        }

        public User.Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public User.Builder withSolAddress(String solAddress) {
            this.solAddress = solAddress;
            return this;
        }

        public User.Builder withUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public User.Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public User.Builder withWallet(Wallet wallet) {
            this.wallet = wallet;
            return this;
        }

        public User build() {
            User user = new User();
            user.createdAt = createdAt;
            user.email = email;
            user.emailVerified = emailVerified;
            user.ethAddress = ethAddress;
            user.id = id;
            user.solAddress = solAddress;
            user.updatedAt = updatedAt;
            user.username = username;
            user.wallet = wallet;
            return user;
        }

    }

}

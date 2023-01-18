package com.mirror.sdk.response.action;

public class ActionDTO {
    public int id;
    public String uuid;
    public String client_id;
    public int user_id;
    public String status;
    public String type;
    public Object signature;
    public String message;
    public double value;
    public ParamsDTO params;
    public String origin;
    public DeviceDTO device;
    public String started_at;
    public String verified_at;
    public Object cancelled_at;
    public Object completed_at;
    public String createdAt;
    public String updatedAt;

    public static class ParamsDTO {
//        public int amount;
//        public String to_publickey;
        public String url;
        public String name;
        public String symbol;
        public String confirmation;
        public String collection_mint;
    }

    public static class DeviceDTO {
        public Object os;
        public Object bot;
        public ClientDTO client;
        public Object device;

        public static class ClientDTO {
            public String name;
            public String type;
            public String version;
        }
    }
}
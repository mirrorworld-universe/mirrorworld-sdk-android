package com.mirror.sdk.ui.market.widgets.multiple.adapter;


public class ActivityItem {

    private String event;
    private String price;
    private String data;


    public ActivityItem(String event,String price,String data){
        this.event = event;
        this.price = price;
        this.data = data;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

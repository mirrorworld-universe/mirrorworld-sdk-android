package com.mirror.sdk.models;

import java.util.List;

public class NFTJsonObject<E> {
    public String name;
    public String symbol;
    public String description;
    public String image;
    public double seller_fee_basis_points;
    public CollectionJsonObject collection;
    public List<E> attributes;
}

class CollectionJsonObject{
    public String name;
    public String family;
}
package com.project.ecommerce.data.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class OrderDetailModel {
    @SerializedName("id") Integer id;
    @SerializedName("price")@Nullable  String price;
    @SerializedName("quantity")@Nullable  String quantity;
    @SerializedName("product")@Nullable ProductModel product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Nullable
    public String getPrice() {
        return price;
    }

    public void setPrice(@Nullable String price) {
        this.price = price;
    }

    @Nullable
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(@Nullable String quantity) {
        this.quantity = quantity;
    }

    @Nullable
    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(@Nullable ProductModel product) {
        this.product = product;
    }
}


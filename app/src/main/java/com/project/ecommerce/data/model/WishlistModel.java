package com.project.ecommerce.data.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "wishlist_table")
public class WishlistModel {
    @PrimaryKey
    @SerializedName("product_id") Integer id;
    @SerializedName("title")@Nullable String title;
    @SerializedName("desc")@Nullable  String desc;
    @SerializedName("imageUrl")@Nullable  String imageUrl;
    @SerializedName("price")@Nullable  String price;
    @SerializedName("wishlist")@Nullable  Boolean wishlist;
    @SerializedName("stock")@Nullable  Integer stock;

    @SerializedName("quantity")@Nullable  Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getWishlist() {
        return wishlist;
    }

    public void setWishlist(Boolean wishlist) {
        this.wishlist = wishlist;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}


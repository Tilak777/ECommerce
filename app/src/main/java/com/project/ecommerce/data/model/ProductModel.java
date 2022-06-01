package com.project.ecommerce.data.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import kotlinx.serialization.Serializable;

@Entity(tableName = "product_table")
@Serializable
public class ProductModel implements java.io.Serializable {
    @PrimaryKey
    @SerializedName("id") Integer id;
    @SerializedName("title") @Nullable String title;
    @SerializedName("description") @Nullable String desc;
    @SerializedName("feature_image") @Nullable String imageUrl;
    @SerializedName("price") @Nullable String price;
    @SerializedName("wishlist") @Nullable Boolean wishlist;
    @SerializedName("stock") @Nullable Integer stock;
    @SerializedName("quantity") @Nullable Integer quantity;

//    public ProductModel(Integer id, String title, String imageUrl, Double price, Integer stock) {
//        this.id = id;
//        this.title = title;
//        this.imageUrl = imageUrl;
//        this.price = price;
//        this.stock = stock;
//    }
    public ProductModel(
            Integer id,
            @Nullable String title,
            @Nullable String imageUrl,
            @Nullable String price,
            @Nullable Integer stock,
            @Nullable String desc
    ) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.price = price;
        this.stock = stock;
        this.desc = desc;
    }

    public WishlistModel convertToWishList(){
        WishlistModel model = new WishlistModel();
        model.id = id;
        model.title = title;
        model.desc = desc;
        model.imageUrl = imageUrl;
        model.price = price;
        model.stock = stock;
        model.quantity = this.quantity;
        return model;
    }

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

    @Nullable
    public String getPrice() {
        return price;
    }

    public void setPrice(@Nullable String price) {
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

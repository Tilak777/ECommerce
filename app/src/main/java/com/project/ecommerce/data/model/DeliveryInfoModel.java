package com.project.ecommerce.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Nullable;

public class DeliveryInfoModel {

    @SerializedName("name") @Nullable String name;
    @SerializedName("phone") @Nullable String phone;
    @SerializedName("email") @Nullable String email;
    @SerializedName("landmark") @Nullable String landmark;
    @SerializedName("region") @Nullable String region;
    @SerializedName("address") @Nullable String address;
    @SerializedName("latitude") @Nullable String latitude;
    @SerializedName("longitude") @Nullable String longitude;

    @SerializedName("products") @Nullable
    List<WishlistModel> carts;

    @Nullable
    public List<WishlistModel> getCarts() {
        return carts;
    }

    public void setCarts(@Nullable List<WishlistModel> carts) {
        this.carts = carts;
    }

    public DeliveryInfoModel(@Nullable String name, @Nullable String phone, @Nullable String email, @Nullable String building, @Nullable String city, @Nullable String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.landmark = building;
        this.region = city;
        this.address = address;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(@Nullable String landmark) {
        this.landmark = landmark;
    }

    @Nullable
    public String getRegion() {
        return region;
    }

    public void setRegion(@Nullable String region) {
        this.region = region;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }
}

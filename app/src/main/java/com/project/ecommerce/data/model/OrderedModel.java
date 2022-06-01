package com.project.ecommerce.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderedModel {
    @SerializedName("id") Integer id;
    @SerializedName("order_code")@Nullable  String orderCode;
    @SerializedName("total_amount")@Nullable  String totalAmount;
    @SerializedName("order_status")@Nullable  String orderStatus;
    @SerializedName("created_at")@Nullable  String createdAt;
    @SerializedName("userDetails")@Nullable  UserModel user;
    @SerializedName("orderDetails")@Nullable List<OrderDetailModel> orderDetails;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Nullable
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(@Nullable String orderCode) {
        this.orderCode = orderCode;
    }

    @Nullable
    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(@Nullable String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Nullable
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(@Nullable String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Nullable
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@Nullable String createdAt) {
        this.createdAt = createdAt;
    }

    @Nullable
    public UserModel getUser() {
        return user;
    }

    public void setUser(@Nullable UserModel user) {
        this.user = user;
    }

    @Nullable
    public List<OrderDetailModel> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(@Nullable List<OrderDetailModel> orderDetails) {
        this.orderDetails = orderDetails;
    }
}


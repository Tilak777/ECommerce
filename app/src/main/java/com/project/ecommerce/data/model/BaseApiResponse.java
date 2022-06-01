package com.project.ecommerce.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Nullable;

public class BaseApiResponse<T> {

    public BaseApiResponse(@Nullable T data){
        this.data = data;
    }

    @SerializedName("status")
    boolean status = false;

    @SerializedName("code")
    int code;

    @SerializedName("data")
    @Nullable T data;

    @SerializedName("message")
    String message;

    @SerializedName("error_message")
    String errorMsg;

    @SerializedName("succeed")
    boolean succeeded = false;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }
}

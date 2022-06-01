package com.project.ecommerce.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

@Entity(tableName = "user_table")
public class UserModel {

    @PrimaryKey
    @SerializedName("id") Integer id;
    @SerializedName("name") @Nullable String firstName;
    @SerializedName("lastName") @Nullable String lastName;
    @SerializedName("email") @Nullable String email;
    @SerializedName("countryCode") @Nullable String countryCode;
    @SerializedName("phone") @Nullable String phone;
    @SerializedName("profileImg") @Nullable String profileImg;
    @SerializedName("gender") @Nullable String gender;
    @SerializedName("dob") @Nullable String dob;
    @SerializedName("token") @Nullable String accessToken;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(@Nullable String countryCode) {
        this.countryCode = countryCode;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(@Nullable String profileImg) {
        this.profileImg = profileImg;
    }

    @Nullable
    public String getGender() {
        return gender;
    }

    public void setGender(@Nullable String gender) {
        this.gender = gender;
    }

    @Nullable
    public String getDob() {
        return dob;
    }

    public void setDob(@Nullable String dob) {
        this.dob = dob;
    }

    @Nullable
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(@Nullable String accessToken) {
        this.accessToken = accessToken;
    }
}

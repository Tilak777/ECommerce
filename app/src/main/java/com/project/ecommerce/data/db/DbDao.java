package com.project.ecommerce.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.data.model.UserModel;
import com.project.ecommerce.data.model.WishlistModel;

import java.util.List;

import javax.annotation.Nullable;

@Dao
public interface DbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(UserModel userModel);
    @Query("Select * from user_table")
    @Nullable UserModel getUser();
    @Query("Select * from user_table")
    @Nullable LiveData<UserModel> getUserLD();
    @Query("Delete From user_table")
    void deleteUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCart(ProductModel model);
    @Query("Select * from product_table")
    List<ProductModel> getCart();
    @Query("Select * from product_table where id=:id")
    ProductModel getCartById(Integer id);
    @Query("Select * from product_table where id=:id")
    @Nullable LiveData<ProductModel> getCartByIdLD(Integer id);
    @Query("Select * from product_table")
    LiveData<List<ProductModel>> getCartLD();
    @Query("Delete From product_table")
    void deleteAllCart();
    @Query("Delete From product_table where id=:id")
    void deleteCart(Integer id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addWishlist(WishlistModel userModel);
    @Query("Select * from wishlist_table")
    List<WishlistModel> getWishlist();
    @Query("Select * from wishlist_table where id=:id")
    WishlistModel getWishlistById(Integer id);
    @Query("Select * from wishlist_table")
    LiveData<List<WishlistModel>> getWishlistLD();
    @Query("Delete From wishlist_table")
    void deleteAllWishlist();
    @Query("Delete From wishlist_table where id=:id")
    void deleteWishlist(Integer id);

}

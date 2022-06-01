package com.project.ecommerce.ui.listener;

import com.project.ecommerce.data.model.ProductModel;

import java.util.List;

public interface ProductListener {
    void viewProduct(ProductModel model,int position);
    void addToCart(ProductModel model,int position);
    void removeFromCart(ProductModel model,int position);
}

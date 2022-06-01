package com.project.ecommerce.ui.listener;

import com.project.ecommerce.data.model.ProductModel;

import java.util.List;

public interface ProductLoadListener {
    void loadProducts(List<ProductModel> list);
    void loadProduct(ProductModel model);
    void loadError(String msg);
}

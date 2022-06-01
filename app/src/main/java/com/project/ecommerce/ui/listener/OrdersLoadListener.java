package com.project.ecommerce.ui.listener;

import com.project.ecommerce.data.model.OrderedModel;

import java.util.List;

public interface OrdersLoadListener {
    void success(List<OrderedModel> orders, String msg);
    void failure(String msg);
}

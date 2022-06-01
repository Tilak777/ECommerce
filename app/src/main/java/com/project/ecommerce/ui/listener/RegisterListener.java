package com.project.ecommerce.ui.listener;

import com.project.ecommerce.data.model.UserModel;

public interface RegisterListener {
    void registerSuccess(UserModel model,String msg);
    void registerFailure(String msg);
}

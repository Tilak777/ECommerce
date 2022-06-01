package com.project.ecommerce.ui.listener;

import com.project.ecommerce.data.model.UserModel;

public interface LoginListener {
    public void loginSuccess(UserModel model,String message);
    public void loginFailure(String msg);
}

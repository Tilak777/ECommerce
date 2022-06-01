package com.project.ecommerce.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.project.ecommerce.data.model.UserModel;
import com.project.ecommerce.databinding.ActivitySplashBinding;
import com.project.ecommerce.ui.base.BaseActivity;
import com.project.ecommerce.ui.fragments.HomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
public class SplashActivity extends BaseActivity {

    HomeViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            UserModel user = new UserModel();
            user.setId(1);
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setEmail("tilakacharya903@gmail.com");
            user.setProfileImg("https://th.bing.com/th/id/OIP.8my8Yo6F9K_w_c9cudOriQHaE7?w=300&h=200&c=7&r=0&o=5&dpr=1.12&pid=1.7");

            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        }, 1000);

    }
}

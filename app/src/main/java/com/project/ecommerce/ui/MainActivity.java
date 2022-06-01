package com.project.ecommerce.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.project.ecommerce.R;
import com.project.ecommerce.databinding.ActivityMainBinding;
import com.project.ecommerce.ui.base.BaseActivity;
import com.project.ecommerce.ui.fragments.HomeViewModel;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {

    HomeViewModel viewModel;
    ActivityMainBinding binding;
    NavController controller;

    TextView cartCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        controller = Navigation.findNavController(this,R.id.fragContainerView);

        setUpToolbarMenu();
        initUI();
        getCurrentUser();
        checkCartCount();
        checkWishlistCount();
    }

    @SuppressLint("NonConstantResourceId")
    public void initUI(){

        binding.navView.setNavigationItemSelectedListener(item -> {
            binding.drawer.closeDrawer(GravityCompat.START);
            if(item.getItemId() == R.id.menu_home){
                if(controller.getCurrentDestination().getId() != R.id.nav_home){
                    controller.navigate(R.id.homeFragment);
                }
                return false;
            }
            else{
                if(viewModel.getUser() == null){
                    controller.navigate(R.id.authFragment);
                }
                else{
                    controller.navigate(R.id.profileFragment);
                }
                return false;
            }
        });

        binding.toolbar.setNavigationOnClickListener(view ->{
            controller.popBackStack();
        });

        controller.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            int destinationId = navDestination.getId();
//            binding.title.setText(navDestination.getLabel());
            binding.toolbar.setTitle(navDestination.getLabel());

            if(destinationId == R.id.homeFragment){

                binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                binding.toolbar.setNavigationOnClickListener(view1 -> {

                    binding.drawer.openDrawer(GravityCompat.START);

//                    if(viewModel.getUser() == null){
//                        controller.navigate(R.id.authFragment);
//                    }
//                    else{
//
//                    }
                });
            }
            else{
                binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                binding.toolbar.setNavigationOnClickListener(view1 -> {
                    controller.popBackStack();
                });
            }


            switch (destinationId){

                case R.id.homeFragment:
                    binding.toolbar.setNavigationIcon(R.drawable.ic_list);
                    binding.bnvHome.getMenu().findItem(R.id.nav_home).setChecked(true);
                    binding.appBar.setVisibility(View.VISIBLE);
                    binding.mcvBtmNav.setVisibility(View.VISIBLE);
                    break;
                case R.id.nav_order:
                    binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
                    binding.bnvHome.getMenu().findItem(R.id.nav_order).setChecked(true);
                    binding.appBar.setVisibility(View.VISIBLE);
                    binding.mcvBtmNav.setVisibility(View.VISIBLE);
                    break;

                case R.id.authFragment:
                case R.id.loginFragment:
                case R.id.registerFragment:
                case R.id.forgotPasswordFragment:
                case R.id.resetPasswordFragment:
                    binding.appBar.setVisibility(View.GONE);
                    binding.mcvBtmNav.setVisibility(View.GONE);
                    break;


                case R.id.wishlistFragment:
                    binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
                    binding.appBar.setVisibility(View.VISIBLE);
                    binding.mcvBtmNav.setVisibility(View.VISIBLE);
                    break;

                case R.id.cartFragment:
                case R.id.searchFragment:
                case R.id.profileFragment:
                case R.id.productDetailFragment:
                case R.id.deliveryInfoFragment:
                case R.id.orderFragment:
                    binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
                    binding.appBar.setVisibility(View.VISIBLE);
                    binding.mcvBtmNav.setVisibility(View.INVISIBLE);
                    break;

                default:
                    binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
                    binding.appBar.setVisibility(View.VISIBLE);
                    binding.mcvBtmNav.setVisibility(View.INVISIBLE);
                    Timber.d("default Case");
                    break;
            }


        });

        binding.bnvHome.setOnItemSelectedListener(item ->
            {
                int itemId = item.getItemId();
                switch(itemId){
                    case R.id.nav_home:
                        controller.navigate(R.id.homeFragment);
                        break;
                    case R.id.nav_cart:
                        controller.navigate(R.id.cartFragment);
                        break;
                    case R.id.nav_order:
                        if(viewModel.getUser() == null){
//                            return false;
                            controller.navigate(R.id.authFragment);
                            break;
                        }
                        controller.navigate(R.id.orderFragment);
                        break;
                    case R.id.nav_profile:
                        if(viewModel.getUser() == null){
                            controller.navigate(R.id.authFragment);
                        }
                        else{
                            controller.navigate(R.id.profileFragment);
                        }
                        break;
                    case R.id.nav_auth:
                        controller.navigate(R.id.authFragment);
                        break;
                }
                return true;
            }
        );

    }

    @SuppressLint("NonConstantResourceId")
    public void setUpToolbarMenu(){
        Menu menu = binding.toolbar.getMenu();
        MenuItem cartMenu = menu.findItem(R.id.nav_cart);
        View actionCartView = cartMenu.getActionView();
        actionCartView.setOnClickListener(view -> {
            if(controller.getCurrentDestination().getId() != R.id.cartFragment){
                controller.navigate(R.id.cartFragment);
            }
        });
        cartCount = actionCartView.findViewById(R.id.tvBadge);
    }

    public void getCurrentUser(){
        viewModel.getUserLD().observe(this, userModel -> {
            if(userModel != null){
                binding.navView.getMenu().findItem(R.id.menu_auth).setTitle("Profile");
//                binding.bnvHome.getMenu().getItem(2).setVisible(true);
//                binding.bnvHome.getMenu().getItem(3).setVisible(false);
//                Glide.with(this).load(userModel.getProfileImg()).into(profileImage);
            }
            else{
                binding.navView.getMenu().findItem(R.id.menu_auth).setTitle("Login/Register");
                controller.navigate(R.id.authFragment);
//                binding.bnvHome.getMenu().getItem(2).setVisible(false);
//                binding.bnvHome.getMenu().getItem(3).setVisible(true);
            }
        });
    }

    public void checkCartCount(){
        viewModel.getUserCartLD().observe(this, carts -> {
//            BadgeDrawable badge = binding.bnvHome.getOrCreateBadge(R.id.nav_cart);
//            badge.setNumber(carts.size());
//            badge.setVisible(!carts.isEmpty());
//            badge.setBackgroundColor(ContextCompat.getColor(this,R.color.badge_bg));
//            badge.setBadgeTextColor(ContextCompat.getColor(this,R.color.badge_text));

            cartCount.setText(""+carts.size());
            if(carts.isEmpty()) {
                cartCount.setVisibility(View.INVISIBLE);
            } else {
                cartCount.setVisibility(View.VISIBLE);
            }

        });
    }

    public void checkWishlistCount(){
//        viewModel.getUserWishlist().observe(this, wishlist -> {
//            BadgeDrawable badge = binding.bnvHome.getOrCreateBadge(R.id.nav_wishlist);
//            badge.setNumber(wishlist.size());
////            badge.setVisible(!wishlist.isEmpty());
//            badge.setBackgroundColor(ContextCompat.getColor(this,R.color.badge_bg));
//            badge.setBadgeTextColor(ContextCompat.getColor(this,R.color.badge_text));
//        });
    }

    @Override
    public void onBackPressed() {
        try{
            if(binding.drawer.isDrawerOpen(GravityCompat.START))
            {
                binding.drawer.closeDrawer(GravityCompat.START);
            }
            else if(controller.getCurrentDestination().getId() != R.id.homeFragment){
                controller.popBackStack();
            }
            else{
                super.onBackPressed();
            }
        }catch (Exception e){
            super.onBackPressed();
        }

    }
}

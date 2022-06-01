package com.project.ecommerce.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.project.ecommerce.R;
import com.project.ecommerce.data.model.DeliveryInfoModel;
import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.data.model.UserModel;
import com.project.ecommerce.data.model.WishlistModel;
import com.project.ecommerce.databinding.FragmentDeliveryInformationBinding;
import com.project.ecommerce.databinding.FragmentRegisterBinding;
import com.project.ecommerce.ui.base.BaseFragment;
import com.project.ecommerce.ui.listener.DeliveryInfoListener;
import com.project.ecommerce.ui.listener.RegisterListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeliveryInfoFragment extends BaseFragment {

    private FragmentDeliveryInformationBinding binding;

    HomeViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDeliveryInformationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbar.setNavigationOnClickListener(view1 -> {
            Navigation.findNavController(view1).popBackStack();
        });


        binding.btnSave.setOnClickListener(view1 -> {

            binding.tilName.setErrorEnabled(false);
            binding.tilPhone.setErrorEnabled(false);
            binding.tilEmail.setErrorEnabled(false);
            binding.tilBuilding.setErrorEnabled(false);
            binding.tilCity.setErrorEnabled(false);
            binding.tilAddress.setErrorEnabled(false);

            String name = binding.tielName.getText().toString();
            if(name.isEmpty()){
                binding.tilName.setError("Empty Name");
                return;
            }
            String phone = binding.tielPhone.getText().toString();
            if(phone.isEmpty()){
                binding.tilPhone.setError("Empty Phone Number");
                return;
            }
            String email = binding.tielEmail.getText().toString();
            if(email.isEmpty()){
                binding.tilEmail.setError("Empty Email");
                return;
            }
            if(!Pattern.matches(Patterns.EMAIL_ADDRESS.toString(),email)){
                binding.tilEmail.setError("Please Enter Valid Email");
                return;
            }
            String city = binding.tielCity.getText().toString();
            if(city.isEmpty()){
                binding.tilCity.setError("Empty City Name");
                return;
            }
            String building = binding.tielBuilding.getText().toString();
            if(building.isEmpty()){
                binding.tilBuilding.setError("Empty Nearby Landmarks");
                return;
            }

            String address = binding.tielAddress.getText().toString();
            if(address.isEmpty()){
                binding.tilAddress.setError("Empty Address");
                return;
            }

            DeliveryInfoListener listener = new DeliveryInfoListener() {
                @Override
                public void success( String msg) {
                    binding.progress.setVisibility(View.GONE);
                    viewModel.removeAllProduct();
                    showMessage("Thank you for your order.");
                    getMyNavController().popBackStack(R.id.homeFragment,false);
                }

                @Override
                public void failure(String msg) {
                    binding.progress.setVisibility(View.GONE);
                    showMessage(msg);
                }
            };
            DeliveryInfoModel model = new DeliveryInfoModel(name,phone,email,building,city,address);
            List<ProductModel> carts = viewModel.getUserCart();
            ArrayList<WishlistModel> item = new ArrayList<>();
            for (ProductModel cart:
                    carts) {
                WishlistModel a = cart.convertToWishList();
                item.add(a);
            }

            model.setCarts(item);
            binding.progress.setVisibility(View.VISIBLE);
            viewModel.saveDeliveryInfo(listener,model);



//            RegisterListener listener = new RegisterListener() {
//                @Override
//                public void registerSuccess(UserModel model,String msg) {
//                    binding.progress.setVisibility(View.GONE);
////                    getMyNavController().popBackStack(R.id.profileFragment,false);
//                    getMyNavController().navigate(R.id.action_registerFragment_to_profileFragment);
//                    showMessage(msg);
//                }
//
//                @Override
//                public void registerFailure(String msg) {
//                    binding.progress.setVisibility(View.GONE);
//                    showMessage(msg);
//                }
//            };
//            binding.progress.setVisibility(View.VISIBLE);
//            viewModel.register(listener,name,email,pwd,confirmPwd);


        });

    }
}

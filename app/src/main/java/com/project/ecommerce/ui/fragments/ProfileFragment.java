package com.project.ecommerce.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.project.ecommerce.R;
import com.project.ecommerce.databinding.FragmentProfileBinding;
import com.project.ecommerce.databinding.FragmentSearchBinding;
import com.project.ecommerce.ui.base.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends BaseFragment {

    private FragmentProfileBinding binding;

    HomeViewModel viewModel;

    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            remove();
            if(isAdded()){
                Navigation.findNavController(requireActivity(),R.id.fragContainerView).popBackStack(R.id.homeFragment, false);
            }
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        requireActivity().getOnBackPressedDispatcher().addCallback(callback);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogout.setOnClickListener(view1 -> {
            viewModel.logout();
        });


        viewModel.getUserLD().observe(getViewLifecycleOwner(), userModel -> {
            if(userModel != null){

                if(userModel.getProfileImg() != null && !userModel.getProfileImg().isEmpty()){
                    Glide.with(this).load(userModel.getProfileImg()).into(binding.ivProfile);
                }
                binding.tvName.setText(userModel.getFirstName());
                binding.tvEmail.setText(userModel.getEmail());
            }
            else{
                getMyNavController().popBackStack(R.id.homeFragment,false);
            }
        });
    }
}

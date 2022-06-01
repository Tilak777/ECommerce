package com.project.ecommerce.ui.fragments.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.project.ecommerce.R;
import com.project.ecommerce.databinding.FragmentAuthBinding;
import com.project.ecommerce.databinding.FragmentResetPasswordBinding;
import com.project.ecommerce.ui.fragments.HomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResetPasswordFragment extends Fragment {

    private FragmentResetPasswordBinding binding;

    HomeViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbar.setNavigationOnClickListener(view1 -> {
            Navigation.findNavController(view1).popBackStack();
        });
        binding.btnReset.setOnClickListener(view1 -> {
//            Navigation.findNavController(view1).navigate(R.id.searchFragment);
            Navigation.findNavController(view1).popBackStack(R.id.homeFragment,false);
        });
    }
}

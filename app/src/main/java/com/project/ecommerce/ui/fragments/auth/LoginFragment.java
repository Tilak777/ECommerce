package com.project.ecommerce.ui.fragments.auth;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.project.ecommerce.R;
import com.project.ecommerce.data.model.UserModel;
import com.project.ecommerce.databinding.FragmentAuthBinding;
import com.project.ecommerce.databinding.FragmentLoginBinding;
import com.project.ecommerce.ui.base.BaseFragment;
import com.project.ecommerce.ui.fragments.HomeViewModel;
import com.project.ecommerce.ui.listener.LoginListener;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;

    HomeViewModel viewModel;

    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            remove();
            if(isAdded()){
                Navigation.findNavController(requireActivity(),R.id.fragContainerView).popBackStack(R.id.profileFragment, false);
            }
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        requireActivity().getOnBackPressedDispatcher().addCallback(callback);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(viewModel.getUser() != null){

            getMyNavController().popBackStack();

//            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    callback.handleOnBackPressed();
//                    getMyNavController().popBackStack();
//                }
//            },250);
        }

        binding.toolbar.setNavigationOnClickListener(view1 -> {
            requireActivity().onBackPressed();
        });

        binding.tielEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                binding.tilEmail.setError("");
            }
        });
        binding.tielPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                binding.tilPassword.setError("");
            }
        });

        binding.btnLogin.setOnClickListener(view1 -> {
            binding.tilEmail.setErrorEnabled(false);
            binding.tilPassword.setErrorEnabled(false);

            String email = binding.tielEmail.getText().toString();
            if(email.isEmpty()){
                binding.tilEmail.setError("Empty Email/Phone");
                return;
            }
            String pwd = binding.tielPassword.getText().toString();
            if(pwd.isEmpty()){
                binding.tilPassword.setError("Empty Password");
                return;
            }

            LoginListener listener = new LoginListener() {
                @Override
                public void loginSuccess(UserModel model, String message) {
                    binding.progress.setVisibility(View.GONE);
//                    callback.handleOnBackPressed();
//                    getMyNavController().popBackStack(R.id.profileFragment,false);
                    getMyNavController().navigate(R.id.action_loginFragment_to_profileFragment);
                    showMessage(message);
                }

                @Override
                public void loginFailure(String msg) {
                    binding.progress.setVisibility(View.GONE);
                    showMessage(msg);
                }
            };
            binding.progress.setVisibility(View.VISIBLE);
            viewModel.login(listener,email,pwd);

        });

        binding.tvForgotPassword.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
        });
        binding.tvSignup.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.registerFragment);
        });
    }
}

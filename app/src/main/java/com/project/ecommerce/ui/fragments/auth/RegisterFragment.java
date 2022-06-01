package com.project.ecommerce.ui.fragments.auth;

import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.project.ecommerce.R;
import com.project.ecommerce.data.model.UserModel;
import com.project.ecommerce.databinding.FragmentRegisterBinding;
import com.project.ecommerce.ui.base.BaseFragment;
import com.project.ecommerce.ui.fragments.HomeViewModel;
import com.project.ecommerce.ui.listener.RegisterListener;

import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends BaseFragment {

    private FragmentRegisterBinding binding;

    HomeViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(viewModel.getUser() != null){
            getMyNavController().popBackStack();
        }

        binding.toolbar.setNavigationOnClickListener(view1 -> {
            Navigation.findNavController(view1).popBackStack();
        });

        binding.tvSignIn.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.loginFragment);
        });


        binding.tielName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                binding.tilName.setError("");
            }
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
        binding.tielConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                binding.tilConfirmPassword.setError("");
            }
        });


        binding.btnRegister.setOnClickListener(view1 -> {
            binding.tilName.setErrorEnabled(false);
            binding.tilEmail.setErrorEnabled(false);
            binding.tilPassword.setErrorEnabled(false);
            binding.tilConfirmPassword.setErrorEnabled(false);

            String name = binding.tielName.getText().toString();
            if(name.isEmpty()){
                binding.tilName.setError("Empty Name");
                return;
            }
            String email = binding.tielEmail.getText().toString();
            if(email.isEmpty()){
                binding.tilEmail.setError("Empty Email/Phone");
                return;
            }
            if(!Pattern.matches(Patterns.EMAIL_ADDRESS.toString(),email)){
                binding.tilEmail.setError("Please Enter Valid Email");
                return;
            }
            String pwd = binding.tielPassword.getText().toString();
            if(pwd.isEmpty()){
                binding.tilPassword.setError("Empty Password");
                return;
            }
            String confirmPwd = binding.tielConfirmPassword.getText().toString();
            if(confirmPwd.isEmpty()){
                binding.tilConfirmPassword.setError("Re-type your Password");
                return;
            }
            if(!pwd.equals(confirmPwd)){
                binding.tilConfirmPassword.setError("Password doesn't match.");
                return;
            }

            RegisterListener listener = new RegisterListener() {
                @Override
                public void registerSuccess(UserModel model,String msg) {
                    binding.progress.setVisibility(View.GONE);
//                    getMyNavController().popBackStack(R.id.profileFragment,false);
                    getMyNavController().navigate(R.id.action_registerFragment_to_profileFragment);
                    showMessage(msg);
                }

                @Override
                public void registerFailure(String msg) {
                    binding.progress.setVisibility(View.GONE);
                    showMessage(msg);
                }
            };
            binding.progress.setVisibility(View.VISIBLE);
            viewModel.register(listener,name,email,pwd,confirmPwd);


        });

    }
}

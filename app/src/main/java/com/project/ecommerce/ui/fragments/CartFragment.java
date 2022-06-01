package com.project.ecommerce.ui.fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.ecommerce.R;
import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.databinding.FragmentCartBinding;
import com.project.ecommerce.databinding.FragmentHomeBinding;
import com.project.ecommerce.databinding.ItemCartBinding;
import com.project.ecommerce.databinding.ItemProductBinding;
import com.project.ecommerce.ui.adapters.ProductAdapter;
import com.project.ecommerce.ui.base.BaseFragment;
import com.project.ecommerce.ui.listener.ProductListener;
import com.project.ecommerce.ui.listener.ProductLoadListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends BaseFragment {

    private FragmentCartBinding binding;

    HomeViewModel viewModel;
    CartAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler(viewModel.getUserCart());
        updateTotal(viewModel.getUserCart());
    }

    public void initRecycler(List<ProductModel> carts){
        ProductListener listener = new ProductListener() {
            @Override
            public void viewProduct(ProductModel model, int position) {

            }

            @Override
            public void addToCart(ProductModel model, int position) {
                viewModel.addProduct(model);
                adapter.list.set(position,model);
                adapter.notifyItemChanged(position);
                updateTotal(adapter.list);
            }

            @Override
            public void removeFromCart(ProductModel model, int position) {
                viewModel.removeProduct(model);
                adapter.list.remove(position);
                adapter.notifyDataSetChanged();
                updateTotal(adapter.list);
            }
        };
        adapter = new CartAdapter(requireContext(),listener,carts);
        binding.rv.setLayoutManager(new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false));
        binding.rv.setAdapter(adapter);
    }


    public void updateTotal(List<ProductModel> models){
        if(models.isEmpty()){
            binding.tvCartTotal.setText("$ 0");
            binding.tvCartCheckout.setOnClickListener( view -> {
                showMessage("Empty Cart");
            });
        }
        else{
            float total = 0f;
            for (ProductModel cart: models) {
                int price = Integer.parseInt(cart.getPrice());
                total = total + (price * cart.getQuantity());
            }
            binding.tvCartTotal.setText("$ "+total);
            binding.tvCartCheckout.setOnClickListener( view -> {
                if(viewModel.getUser() == null){
                    getMyNavController().navigate(R.id.authFragment);
                }
                else{
                    getMyNavController().navigate(R.id.action_cartFragment_to_deliveryInfoFragment);
//                    showMessage("Proceed");
                }
            });
        }
    }

    class CartAdapter extends RecyclerView.Adapter<CartAdapter.VH>{
        private Context context;
        List<ProductModel> list;
        ProductListener listener;

        @Nullable ViewGroup.LayoutParams params = null;

        public CartAdapter(Context context,ProductListener listener,List<ProductModel> list){
            this.context = context;
            this.listener = listener;
            this.list = list;
        }

        @NonNull
        @Override
        public CartAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemCartBinding binding = ItemCartBinding.inflate(LayoutInflater.from(context));
            return new CartAdapter.VH(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull CartAdapter.VH holder, int position) {
            holder.bind(list.get(position),position,listener);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class VH extends RecyclerView.ViewHolder{

            ItemCartBinding binding;

            public VH(ItemCartBinding binding){
                super(binding.getRoot());
                this.binding = binding;
            }
            public void bind(ProductModel model, int position, ProductListener listener){

                updateTotal(list);

                binding.tvCartProductName.setText(model.getTitle());
                int quantity = model.getQuantity();
                int price = Integer.parseInt(model.getPrice());
                binding.tvOrderProductPrice.setText("$ "+quantity*price);
                binding.tvCounterText.setText(""+model.getQuantity());
                Glide.with(binding.getRoot().getContext()).load(model.getImageUrl()).into(binding.ivCartImage);

                binding.tvCartRemove.setOnClickListener(view -> {
                    listener.removeFromCart(model,position);
                });

                binding.vCounterDecrese.setOnClickListener(view -> {
                    if(model.getQuantity() > 1){
                        model.setQuantity(model.getQuantity() - 1);
                        listener.addToCart(model,position);
                    }
                });
                binding.vCounterIncrease.setOnClickListener(view -> {
                    if(model.getQuantity() < 99){
                        model.setQuantity(model.getQuantity() + 1);
                        listener.addToCart(model,position);
                    }
                });
            }
        }
    }

}

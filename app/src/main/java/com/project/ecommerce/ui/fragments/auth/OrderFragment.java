package com.project.ecommerce.ui.fragments.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.project.ecommerce.R;
import com.project.ecommerce.data.model.OrderDetailModel;
import com.project.ecommerce.data.model.OrderedModel;
import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.databinding.FragmentCartBinding;
import com.project.ecommerce.databinding.FragmentOrdersBinding;
import com.project.ecommerce.databinding.ItemCartBinding;
import com.project.ecommerce.databinding.ItemMyOrderListBinding;
import com.project.ecommerce.databinding.ItemMyOrderProductBinding;
import com.project.ecommerce.ui.base.BaseFragment;
import com.project.ecommerce.ui.fragments.HomeViewModel;
import com.project.ecommerce.ui.listener.OrdersLoadListener;
import com.project.ecommerce.ui.listener.ProductListener;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderFragment extends BaseFragment {

    private FragmentOrdersBinding binding;

    HomeViewModel viewModel;
    OrderAdapter adapter;
    List<OrderedModel> all;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
        loadData();
    }

    public void initRecycler(){
        adapter = new OrderAdapter(requireContext(), new ArrayList());
        binding.rv.setLayoutManager(new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false));
        binding.rv.setAdapter(adapter);
    }

    public void loadData(){
        OrdersLoadListener listener = new OrdersLoadListener() {
            @Override
            public void success(List<OrderedModel> orders, String msg) {
                all = orders;
                adapter.list = orders;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(String msg) {

            }
        };
        viewModel.getMyOrderHistory(listener);
    }

    class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH>{
        private Context context;
        List<OrderedModel> list;

        @Nullable ViewGroup.LayoutParams params = null;

        public OrderAdapter(Context context,List<OrderedModel> list){
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemMyOrderListBinding binding = ItemMyOrderListBinding.inflate(LayoutInflater.from(context));
            MaterialCardView.LayoutParams params = new MaterialCardView.LayoutParams(MaterialCardView.LayoutParams.MATCH_PARENT,
                    MaterialCardView.LayoutParams.WRAP_CONTENT);
            params.setMargins(20,20,20,0);
            binding.getRoot().setLayoutParams(params);
            return new VH(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            holder.bind(list.get(position),position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class VH extends RecyclerView.ViewHolder{

            ItemMyOrderListBinding binding;

            public VH(ItemMyOrderListBinding binding){
                super(binding.getRoot());
                this.binding = binding;
            }
            public void bind(OrderedModel model, int position){

                binding.tvOrderStatus.setText(model.getOrderStatus());
                binding.tvOrderNo.setText(""+model.getOrderCode());
                binding.tvOrderDate.setText(model.getCreatedAt());
                binding.tvOrderGrandTotal.setText("$ "+model.getTotalAmount());

                OrderItemsAdapter adapter = new OrderItemsAdapter(context,model.getOrderDetails());
                binding.rvProductItem.setAdapter(adapter);
                binding.rvProductItem.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));

            }
        }

    }

    class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.VH>{
        private Context context;
        List<OrderDetailModel> list;

        @Nullable ViewGroup.LayoutParams params = null;

        public OrderItemsAdapter(Context context,List<OrderDetailModel> list){
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemMyOrderProductBinding binding = ItemMyOrderProductBinding.inflate(LayoutInflater.from(context));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(20,20,20,0);
            binding.getRoot().setLayoutParams(params);
            return new VH(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            holder.bind(list.get(position),position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class VH extends RecyclerView.ViewHolder{

            ItemMyOrderProductBinding binding;

            public VH(ItemMyOrderProductBinding binding){
                super(binding.getRoot());
                this.binding = binding;
            }
            public void bind(OrderDetailModel model, int position){

                Glide.with(binding.getRoot().getContext()).load(model.getProduct().getImageUrl()).into(binding.image);
                int quantity = Integer.parseInt(model.getQuantity());
                binding.tvName.setText(model.getProduct().getTitle() + " ("+quantity+ "pc)");
                if(model.getProduct().getDesc() != null){
                    binding.tvDesc.setText(HtmlCompat.fromHtml(model.getProduct().getDesc(),HtmlCompat.FROM_HTML_MODE_COMPACT));
                }
                int price = Integer.parseInt(model.getPrice());
                binding.tvPrice.setText("$ "+price*quantity);

            }
        }

    }

}

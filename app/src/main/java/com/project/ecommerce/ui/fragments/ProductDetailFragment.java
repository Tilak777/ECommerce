package com.project.ecommerce.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.project.ecommerce.R;
import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.databinding.FragmentProductDetailBinding;
import com.project.ecommerce.ui.adapters.GridItemDecoration;
import com.project.ecommerce.ui.adapters.MarginItemDecoration;
import com.project.ecommerce.ui.adapters.ProductAdapter;
import com.project.ecommerce.ui.base.BaseFragment;
import com.project.ecommerce.ui.listener.ProductListener;
import com.project.ecommerce.ui.listener.ProductLoadListener;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetailFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentProductDetailBinding binding;

    HomeViewModel viewModel;
    ProductModel model;
    ProductAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.srl.setOnRefreshListener(this);

        assert getArguments() != null;

        model = (ProductModel) getArguments().getSerializable("data");

        initUI(model);

        loadData();

        viewModel.getUserCart(model.getId()).observe(getViewLifecycleOwner(), item ->{
            if(item == null){
                binding.btnAddCart.setText("Add To Cart");
                binding.btnAddCart.setOnClickListener(view1 -> {
                    model.setQuantity(1);
                    viewModel.addProduct(model);
                });
            }
            else{
                binding.btnAddCart.setText("View in Cart");
                binding.btnAddCart.setOnClickListener(view1 -> {
                    this.getMyNavController().navigate(R.id.cartFragment);
                });
            }
        });

//        loadDummyRelatedProducts();

    }



    public void initRecycler(@Nullable List<ProductModel> list){
        adapter = new ProductAdapter(requireContext(), new ProductListener() {
            @Override
            public void viewProduct(ProductModel model, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",model);
                Navigation.findNavController(requireActivity(), R.id.fragContainerView)
                        .navigate(R.id.action_productDetailFragment_self,bundle);
            }

            @Override
            public void addToCart(ProductModel model, int position) {

            }

            @Override
            public void removeFromCart(ProductModel model, int position) {

            }

        },list);

//        adapter.setWidth(0.8);
//        binding.rv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false));
        binding.rv.setLayoutManager(new GridLayoutManager(requireContext(),2));
        binding.rv.setAdapter(adapter);


        int margin = (int) requireContext().getResources().getDimension(R.dimen.marginMid);
        MarginItemDecoration decoration = new MarginItemDecoration(margin,true);

        GridItemDecoration gridItemDecoration = new GridItemDecoration(2,margin,false);

        binding.rv.removeItemDecoration(gridItemDecoration);
        binding.rv.addItemDecoration(gridItemDecoration);

    }

    @Override
    public void onRefresh() {
        loadData();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.srl.setRefreshing(false);
        },1000);
    }

    private void loadDummyRelatedProducts(){
        ArrayList<ProductModel> dummyList = new ArrayList<>();
        String desc = "Gucci handbags come in a range of sizes and styles. They are small or medium-sized, made of leather, canvas, and suede, and feature zippered compartments and metal locks or magnetic snap closures. Some have adjustable straps, usually made of leather.";
        String testImage1 = "https://th.bing.com/th/id/OIP.1uP-gRCpYkmsa9ZQU96_WwHaJ4?pid=ImgDet&rs=1";
        String testImage2 = "https://th.bing.com/th/id/OIP.NmCFdu8uqccKfcGdINM_YwHaD4?w=320&h=180&c=7&r=0&o=5&dpr=1.12&pid=1.7";
        dummyList.add(new ProductModel(1,"Product A",testImage2,"200.0",2,desc));
        dummyList.add(new ProductModel(2,"Product B",testImage1,"220.0",5,desc));
        dummyList.add(new ProductModel(3,"Product C",testImage2,"999.99",0,desc));

        initRecycler(dummyList);
    }

    private void loadData() {
        ProductLoadListener listener = new ProductLoadListener() {
            @Override
            public void loadProducts(List<ProductModel> list) {
                binding.groupRelatedProduct.setVisibility(View.VISIBLE);
                initRecycler(list);
            }

            @Override
            public void loadProduct(ProductModel model) {
                initUI(model);
            }

            @Override
            public void loadError(String msg) {

            }
        };
        
        viewModel.getProductDetail(listener,model.getId());
        viewModel.getRelatedProducts(listener,model.getId());

    }

    private void initUI(ProductModel model){
        this.model = model;
        Glide.with(binding.getRoot().getContext()).load(model.getImageUrl()).into(binding.image);
        binding.tvName.setText(model.getTitle());
        binding.price.setText("$ "+model.getPrice());
        if(model.getDesc() != null){
//            binding.tvDesc.setText(model.getDesc());
            binding.tvDesc.setText(HtmlCompat.fromHtml(model.getDesc(),HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
    }
}

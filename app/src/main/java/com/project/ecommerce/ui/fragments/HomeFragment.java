package com.project.ecommerce.ui.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.project.ecommerce.R;
import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.databinding.FragmentHomeBinding;
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
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private @Nullable FragmentHomeBinding binding;

    HomeViewModel viewModel;

    ProductAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding == null){
            binding = FragmentHomeBinding.inflate(inflater,container,false);
            loadData();
        }
        return binding.getRoot();
    }

    public void loadDummyData(){
        ArrayList<ProductModel> dummyList = new ArrayList<>();
        String desc = "Gucci handbags come in a range of sizes and styles. They are small or medium-sized, made of leather, canvas, and suede, and feature zippered compartments and metal locks or magnetic snap closures. Some have adjustable straps, usually made of leather.";
        String testImage1 = "https://th.bing.com/th/id/OIP.1uP-gRCpYkmsa9ZQU96_WwHaJ4?pid=ImgDet&rs=1";
        String testImage2 = "https://th.bing.com/th/id/OIP.NmCFdu8uqccKfcGdINM_YwHaD4?w=320&h=180&c=7&r=0&o=5&dpr=1.12&pid=1.7";
        dummyList.add(new ProductModel(1,"Product A",testImage2,"200.0",2,desc));
        dummyList.add(new ProductModel(2,"Product B",testImage2,"220.0",5,desc));
        dummyList.add(new ProductModel(3,"Product C",testImage2,"999.99",0,desc));
        dummyList.add(new ProductModel(4,"Product D",testImage2,"55.00",1,desc));
        dummyList.add(new ProductModel(5,"Product E",testImage2,"195.50",1,desc));
        dummyList.add(new ProductModel(6,"Product F",testImage2,"15D",1,desc));
        dummyList.add(new ProductModel(7,"Product G",testImage1,"75D",1,desc));

        initRecycler(dummyList);
    }

    public void loadData(){

//        loadDummyData();

        ProductLoadListener loadListener = new ProductLoadListener() {
            @Override
            public void loadProducts(List<ProductModel> list) {
                initRecycler(list);
            }

            @Override
            public void loadProduct(ProductModel model) {

            }

            @Override
            public void loadError(String msg) {

            }
        };
        viewModel.allProducts(loadListener);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.srl.setOnRefreshListener(this);
        binding.srl.setRefreshing(false);

        binding.search.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_homeFragment_to_searchFragment);
        });


        viewModel.getUserLD().observe(getViewLifecycleOwner(), userModel -> {
            if(userModel != null){
//                binding.title.setText(userModel.getFirstName() + userModel.getLastName());
            }
        });

    }

    public void initRecycler(@Nullable List<ProductModel> list){
        adapter = new ProductAdapter(requireContext(), new ProductListener() {
            @Override
            public void viewProduct(ProductModel model, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",model);
                Navigation.findNavController(requireActivity(),R.id.fragContainerView)
                        .navigate(R.id.action_homeFragment_to_productDetailFragment,bundle);
            }

            @Override
            public void addToCart(ProductModel model, int position) {
                model.setQuantity(1);
                viewModel.addProduct(model);
                showMessage("Product Added");
            }

            @Override
            public void removeFromCart(ProductModel model, int position) {

            }

        },list);
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
}

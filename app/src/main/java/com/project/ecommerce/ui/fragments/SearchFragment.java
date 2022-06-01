package com.project.ecommerce.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.project.ecommerce.R;
import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.databinding.FragmentHomeBinding;
import com.project.ecommerce.databinding.FragmentSearchBinding;
import com.project.ecommerce.ui.adapters.GridItemDecoration;
import com.project.ecommerce.ui.adapters.MarginItemDecoration;
import com.project.ecommerce.ui.adapters.ProductAdapter;
import com.project.ecommerce.ui.base.BaseFragment;
import com.project.ecommerce.ui.listener.ProductListener;
import com.project.ecommerce.ui.listener.ProductLoadListener;

import java.util.List;
import java.util.concurrent.CancellationException;

import dagger.hilt.android.AndroidEntryPoint;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.ChildHandle;
import kotlinx.coroutines.ChildJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.selects.SelectClause0;

@AndroidEntryPoint
public class SearchFragment extends BaseFragment {

    private FragmentSearchBinding binding;

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
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Nullable Job job;
    ProductLoadListener listener;
    String searchWord = "";
    Handler h = new Handler(Looper.getMainLooper());

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener = new ProductLoadListener(){

            @Override
            public void loadProducts(List<ProductModel> list) {
                binding.searchContainer.setVisibility(View.VISIBLE);
                initRecycler(list);
            }

            @Override
            public void loadProduct(ProductModel model) {

            }

            @Override
            public void loadError(String msg) {

            }
        };


        Runnable searchRunnable = () -> {
            search(searchWord);
        };
        binding.tielSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                searchWord = editable.toString();
                if(searchWord.isEmpty()){
                    binding.searchContainer.setVisibility(View.GONE);
                }
                else {
                    h.removeCallbacks(searchRunnable);
                    h.postDelayed( searchRunnable,500);
                }
            }
        });
    }

    public void search(String search){
        if(viewModel.searchDisposable != null){
            viewModel.disposable.remove(viewModel.searchDisposable);
        }
        viewModel.searchProducts(listener,search);
    }

    public void initRecycler(@Nullable List<ProductModel> list){
        adapter = new ProductAdapter(requireContext(), new ProductListener() {
            @Override
            public void viewProduct(ProductModel model, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",model);
                Navigation.findNavController(requireActivity(), R.id.fragContainerView)
                        .navigate(R.id.action_searchFragment_to_productDetailFragment,bundle);
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

        GridItemDecoration gridItemDecoration = new GridItemDecoration(2,margin,false);

        binding.rv.removeItemDecoration(gridItemDecoration);
        binding.rv.addItemDecoration(gridItemDecoration);

    }
}

package com.project.ecommerce.ui.adapters;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.ecommerce.data.model.ProductModel;
import com.project.ecommerce.databinding.ItemProductBinding;
import com.project.ecommerce.ui.listener.ProductListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {

    private Context context;
    List<ProductModel> list;
    ProductListener listener;

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    Double width = 1.0;

    @Nullable ViewGroup.LayoutParams params = null;

    public ProductAdapter(Context context,ProductListener listener,List<ProductModel> list){
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(context));
//        if(params != null){
//            binding.getRoot().setLayoutParams(params);
//        }
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(list.get(position),position,listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        ItemProductBinding binding;

        public VH(ItemProductBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
        public int getWidth(Context context){
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            return point.x;
        }
        public void bind(ProductModel model, int position, ProductListener listener){

            if(width > 1.0){
                try{
                    binding.container.getLayoutParams().width = (int) (getWidth(context) / width);
                }
                catch (Exception e){

                }
            }

            binding.getRoot().setOnClickListener(view ->{
                listener.viewProduct(model,position);
            });
            binding.title.setText(model.getTitle());
            binding.price.setText("$ "+model.getPrice());
            Glide.with(binding.getRoot().getContext()).load(model.getImageUrl()).into(binding.image);

            binding.ivAddCart.setOnClickListener(view -> {
                listener.addToCart(model,position);
            });
        }
    }

}

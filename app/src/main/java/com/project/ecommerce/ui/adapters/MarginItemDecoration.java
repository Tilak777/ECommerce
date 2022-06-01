package com.project.ecommerce.ui.adapters;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarginItemDecoration extends RecyclerView.ItemDecoration {
    int spaceHeight;
    boolean firstItemSpacing;

    public MarginItemDecoration(int spaceHeight, boolean firstItemSpacing) {
        this.spaceHeight = spaceHeight;
        this.firstItemSpacing = firstItemSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if(firstItemSpacing){
            if(parent.getChildAdapterPosition(view) == 0){
                outRect.left = spaceHeight;
            }
        }
        if(parent.getChildAdapterPosition(view) == parent.getChildCount()){
            outRect.right = spaceHeight;
        }
        else{
            outRect.right =  spaceHeight / 2;
        }
    }
}

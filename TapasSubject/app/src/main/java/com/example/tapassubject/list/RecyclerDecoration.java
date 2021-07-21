package com.example.tapassubject.list;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerDecoration extends RecyclerView.ItemDecoration{
    private int height;

    public RecyclerDecoration(int height)
    {
        this.height = height;
    }

    @Override
    public void getItemOffsets(Rect outRect,View view,RecyclerView parent,RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount()-1)
            outRect.bottom = height;
    }
}

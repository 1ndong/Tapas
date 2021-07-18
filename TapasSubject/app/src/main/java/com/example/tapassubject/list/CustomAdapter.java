package com.example.tapassubject.list;

import com.example.tapassubject.R;
import com.example.tapassubject.data.ThumbInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<ThumbInfo> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.d("indong","asdf");
                }
            });
        }
    }

    public CustomAdapter(List<ThumbInfo> list)
    {
        this.mData = list;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.eachitem,parent,false);

        CustomAdapter.ViewHolder vh = new CustomAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        ThumbInfo info = mData.get(position);

        int width = 0;
        int height = 0;
        if(info.isBookcover())
        {
            width = holder.imageView.getLayoutParams().width;
            height = (int)(width * 1.5);
        }
        else
        {
            width = holder.imageView.getLayoutParams().width;
            height = width;
        }

        holder.imageView.requestLayout();
        holder.imageView.getLayoutParams().width = width;
        holder.imageView.getLayoutParams().height = height;
        holder.imageView.setImageBitmap(info.getBitmap());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

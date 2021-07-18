package com.example.tapassubject.list;

import com.example.tapassubject.R;
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

    private List<Bitmap> mData = null;

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

    public CustomAdapter(List<Bitmap> list)
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
        Bitmap bitmap = mData.get(position);
        holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

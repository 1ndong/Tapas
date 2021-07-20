package com.example.tapassubject.list;

import com.example.tapassubject.R;
import com.example.tapassubject.model.CreatorModel;
import com.example.tapassubject.model.SeriesModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CreatorCustomAdapter extends RecyclerView.Adapter<CreatorCustomAdapter.ViewHodler> {
    public class ViewHodler extends RecyclerView.ViewHolder
    {
        private TextView displayNameView;
        private TextView uniqueNameView;

        public ViewHodler(View itemView) {
            super(itemView);

            displayNameView = itemView.findViewById(R.id.displayname);
            uniqueNameView = itemView.findViewById(R.id.uniquename);
        }
    }

    private List<CreatorModel> mData;

    public CreatorCustomAdapter(List<CreatorModel> list)
    {
        this.mData = list;
    }

    @Override
    public CreatorCustomAdapter.ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.creatoritem,parent,false);

        return new CreatorCustomAdapter.ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(CreatorCustomAdapter.ViewHodler holder, int position) {
        holder.displayNameView.setText(mData.get(position).getDisplay_name());
        holder.uniqueNameView.setText(mData.get(position).getUname());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

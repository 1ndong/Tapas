package com.example.tapassubject.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapassubject.R;
import com.example.tapassubject.model.EpisodeModel;

import java.util.List;
import java.util.StringTokenizer;

public class EpisodeCustomAdapter extends RecyclerView.Adapter<EpisodeCustomAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView scene;
        private TextView viewcnt;
        private TextView createddate;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.episodetitle);
            scene = itemView.findViewById(R.id.episodescene);
            viewcnt = itemView.findViewById(R.id.episodeviewcnt);
            createddate = itemView.findViewById(R.id.episodecreateddate);
        }
    }

    private List<EpisodeModel> mData;

    public EpisodeCustomAdapter(List<EpisodeModel> list)
    {
        this.mData = list;
    }

    @Override
    public EpisodeCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.episodeitem,parent,false);

        return new EpisodeCustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EpisodeCustomAdapter.ViewHolder holder, int position) {
        holder.title.setText("제목:" + mData.get(position).getTitle());
        holder.scene.setText("회차:"+mData.get(position).getScene());
        holder.viewcnt.setText("조회수:"+mData.get(position).getView_cnt());

        //2019-10-29T07:00:00Z
        StringTokenizer st = new StringTokenizer(mData.get(position).getCreated_date(),"T");
        holder.createddate.setText("출시일:"+st.nextToken());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

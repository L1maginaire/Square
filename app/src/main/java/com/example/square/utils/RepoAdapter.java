package com.example.square.utils;

/*
 * Created by l1maginaire on 1/25/18.
*/

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.square.MainFragment;
import com.example.square.R;
import com.example.square.data.models.commitmodel.RepoData;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.Holder> {
    private Context mContext;
    private List<RepoData> data;
    private MainFragment.Callbacks mCallbacks;

    public RepoAdapter(Context context, List<RepoData> list, MainFragment.Callbacks callbacks) {
        mContext = context;
        data = list;
        mCallbacks = callbacks;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.reporecycler_single_item, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if (data == null || data.size() == 0)
            return;
        holder.name.setText(data.get(position).getName());
        holder.stars.setText(data.get(position).getStars().toString());
        holder.forks.setText(data.get(position).getForks().toString());
        holder.itemView.setOnClickListener(v -> mCallbacks.onRepoSelected(data.get(position).getName(),
                data.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView stars;
        private TextView forks;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            stars = (TextView) itemView.findViewById(R.id.starsCount);
            forks = (TextView) itemView.findViewById(R.id.forksCount);
        }
    }
}

package com.example.square.adapters;

/**
 * Created by l1maginaire on 1/25/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.square.R;
import com.example.square.mvp.model.main.RepoData;
import com.example.square.ui.MainFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.Holder> {
    private Context context;
    private List<RepoData> data;
    private MainFragment.Callbacks callbacks;

    public RepoAdapter(Context context, List<RepoData> list, MainFragment.Callbacks callbacks) {
        this.context = context;
        data = list;
        this.callbacks = callbacks;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reporecycler_single_item, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if (data == null || data.size() == 0)
            return;
        holder.name.setText(data.get(position).getName());
        holder.stars.setText(String.valueOf(data.get(position).getStars()));
        holder.forks.setText(String.valueOf(data.get(position).getForks()));
        holder.itemView.setOnClickListener(v -> callbacks.onRepoSelected(data.get(position).getName(),
                data.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.starsCount)
        TextView stars;
        @BindView(R.id.forksCount)
        TextView forks;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.example.square.adapters;

/**
 * Created by l1maginaire on 1/27/18.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.square.ui.BrowserActivity;
import com.example.square.R;
import com.example.square.mvp.model.main.CommitData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.Holder> {
    private Context context;
    private List<CommitData> data;
    private boolean areHidden = true; //todo wtf?

    public CommitAdapter (Context context, List<CommitData> list) {
        this.context = context;
        data = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.commits_recycler_single_item, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if (data == null || data.size() == 0)
            return;
        holder.itemView.setOnClickListener(v -> {
            if(areHidden){
                holder.url.setVisibility(View.VISIBLE);
                holder.message.setVisibility(View.VISIBLE);
                holder.committer.setVisibility(View.VISIBLE);
                areHidden = false;
            } else {
                holder.url.setVisibility(View.GONE);
                holder.message.setVisibility(View.GONE);
                holder.committer.setVisibility(View.GONE);
                areHidden = true;
            }
        });
        CommitData cd = data.get(position);
        holder.sha.setText("SHA: " + cd.getSha());
        holder.author.setText("Author: " +cd.getAuthor());
        holder.date.setText(cd.getDate());
        holder.url.setOnClickListener(v ->
                context.startActivity(BrowserActivity.newIntent(context, Uri.parse(cd.getUrl()))));
        holder.message.setText(data.get(position).getMessage());
        holder.committer.setText("Committer: "+data.get(position).getCommitter());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.comSha)
        TextView sha;
        @BindView(R.id.comAuthor)
        TextView author;
        @BindView(R.id.comDate)
        TextView date;
        @BindView(R.id.comUrl)
        TextView url;
        @BindView(R.id.comMessage)
        TextView message;
        @BindView(R.id.comCommitter)
        TextView committer;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.example.square.utils;

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

import com.example.square.BrowserActivity;
import com.example.square.R;
import com.example.square.data.models.commitmodel.CommitData;

import java.util.List;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.Holder> {
    private Context mContext;
    private List<CommitData> data;
    private boolean areHidden = true;

    public CommitAdapter (Context context, List<CommitData> list) {
        mContext = context;
        data = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.commitsrecycler_single_item, parent, false);
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
        holder.url.setOnClickListener(v -> {
            String url = cd.getUrl();
            Intent i = BrowserActivity.newIntent(mContext, Uri.parse(url));
            mContext.startActivity(i);
        });
        holder.message.setText(data.get(position).getMessage());
        holder.committer.setText("Committer: "+data.get(position).getCommitter());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView sha;
        private TextView author;
        private TextView date;
        private TextView url;
        private TextView message;
        private TextView committer;

        public Holder(View itemView) {
            super(itemView);
            sha = (TextView) itemView.findViewById(R.id.comSha);
            author = (TextView) itemView.findViewById(R.id.comAuthor);
            date = (TextView) itemView.findViewById(R.id.comDate);
            url = (TextView) itemView.findViewById(R.id.comUrl);
            message = (TextView) itemView.findViewById(R.id.comMessage);
            committer = (TextView) itemView.findViewById(R.id.comCommitter);
        }
    }
}

package com.example.square.utils;

/**
 * Created by l1maginaire on 1/27/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.square.R;
import com.example.square.data.models.commitmodel.CommitData;

import java.util.List;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.Holder> {
    private Context mContext;
    private List<CommitData> data;
    private boolean hidden = true;

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
            if(hidden){
                holder.url.setVisibility(View.VISIBLE);
                holder.message.setVisibility(View.VISIBLE);
                holder.committer.setVisibility(View.VISIBLE);
            }
        });

        holder.sha.setText(data.get(position).getSha());
        holder.author.setText(data.get(position).getAuthor());
        holder.date.setText(data.get(position).getDate().toString());
        holder.url.setText(data.get(position).getUrl().toString());
        holder.message.setText(data.get(position).getMessage().toString());
        holder.committer.setText(data.get(position).getCommitter().toString());
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

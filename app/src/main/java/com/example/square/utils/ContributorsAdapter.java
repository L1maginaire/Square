package com.example.square.utils;

/**
 * Created by l1maginaire on 1/27/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.square.R;
import com.example.square.data.models.ContributorsData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsAdapter.Holder> {
    private Context mContext;
    private List<ContributorsData> data;
    private Picasso picasso;

    public ContributorsAdapter (Context context, List<ContributorsData> list, Picasso picasso) {
        mContext = context;
        data = list;
        this.picasso = picasso;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.contributors_recycler_single_item, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if (data == null || data.size() == 0)
            return;
        ContributorsData cd = data.get(position);
        holder.login.setText(cd.getLogin());
        holder.contributionsCount.setText(String.valueOf(cd.getContributions()));
        picasso.load(cd.getAvatarUrl()).into(holder.avatar);
//        holder.profile.setText(cd.getProfileUrl());
//        holder.reposurl.setText(cd.getReposUrl());
        /*holder.url.setOnClickListener(v -> {
            String url = cd.getUrl();
            Intent i = BrowserActivity.newIntent(mContext, Uri.parse(url));
            mContext.startActivity(i);
        });*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView login;
        private TextView contributionsCount;
        private TextView profile;
        private TextView reposurl;
        private ImageView avatar;

        public Holder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.conLogin);
            contributionsCount = (TextView) itemView.findViewById(R.id.conContributionsCount);
            reposurl = (TextView) itemView.findViewById(R.id.conReposUrl);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }
}
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.square.R;
import com.example.square.mvp.model.main.ContributorsData;
import com.example.square.ui.BrowserActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsAdapter.Holder> {
    private Context context;
    private List<ContributorsData> data;
    private Picasso picasso;

    public ContributorsAdapter(Context context, List<ContributorsData> list, Picasso picasso) {
        this.context = context;
        data = list;
        this.picasso = picasso;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contributors_recycler_single_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if (data == null || data.size() == 0)
            return;
        ContributorsData cd = data.get(position);
        holder.login.setText(cd.getLogin());
        holder.profileUrl.setOnClickListener(v -> {
            Intent i = BrowserActivity.newIntent(context, Uri.parse(cd.getProfileUrl()));
            context.startActivity(i);
        });
        holder.contributionsCount.setText(String.valueOf(cd.getContributions()));
        picasso.load(cd.getAvatarUrl()).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.conLogin)
        TextView login;
        @BindView(R.id.conContributionsCount)
        TextView contributionsCount;
        @BindView(R.id.conProfileUrl)
        TextView profileUrl;
        @BindView(R.id.avatar)
        ImageView avatar;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

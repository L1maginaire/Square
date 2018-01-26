package com.example.square.utils;

/*
 * Created by l1maginaire on 1/25/18.
*/

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.square.R;
import com.example.square.RepoActivity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context mContext;
    private List<String> data;

    public Adapter(Context context, List<String> list) {
        mContext = context;
        data = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recyclers_single_item, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if (data == null || data.size() == 0)
            return;
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView mImageView;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.result);
            mImageView = (ImageView) itemView.findViewById(R.id.single_item_imageview);
        }

        @Override
        public void onClick(View v) {
            mContext.startActivity(new Intent(mContext, RepoActivity.class));
        }
    }
}

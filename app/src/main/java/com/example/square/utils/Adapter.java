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

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context mContext;
    private List<String> data;
    private MainFragment.Callbacks mCallbacks;

    public Adapter(Context context, List<String> list, MainFragment.Callbacks callbacks) {
        mContext = context;
        data = list;
        mCallbacks = callbacks;
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
        holder.itemView.setOnClickListener(v -> mCallbacks.onRepoSelected(data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.result);
        }
    }
}

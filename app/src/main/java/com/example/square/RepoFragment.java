package com.example.square;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.square.di.components.DaggerSquareComponent;
import com.example.square.di.components.SquareComponent;
import com.example.square.di.modules.ContextModule;
import com.example.square.utils.GithubApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by l1maginaire on 1/26/18.
 */

public class RepoFragment extends Fragment {
//    private Callbacks mCallbacks;
    private GithubApi githubApi;
    private CompositeDisposable mCompositeDisposable;
    private TextView tv;
/*
    public interface Callbacks {
        void onRepoSelected();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }*/

    public static RepoFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        RepoFragment fragment = new RepoFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main2, container, false);
        tv = v.findViewById(R.id.tvtest);
        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(getContext()))
                .build();
        githubApi = component.getGithubService();
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(githubApi.getSquareRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    String t = data.get(0).getArchiveUrl();
                    tv.setText(t);
                })
        );
        return v;
    }
}

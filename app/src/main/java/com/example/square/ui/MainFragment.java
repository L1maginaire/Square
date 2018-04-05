package com.example.square.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.square.R;
import com.example.square.adapters.RepoAdapter;
import com.example.square.api.GithubApi;
import com.example.square.di.components.DaggerSquareComponent;
import com.example.square.di.components.SquareComponent;
import com.example.square.di.modules.ContextModule;
import com.example.square.mvp.model.main.RepoData;
import com.example.square.mvp.model.repos.Repo;
import com.example.square.utils.pagination.EndlessScrollImplementation;
import com.example.square.utils.pagination.PaginationTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by l1maginaire on 1/26/18.
 */

public class MainFragment extends Fragment {
    @BindView(R.id.squareLogo)
    ImageView logo;
    @BindView(R.id.custom_recycler)
    RecyclerView recyclerView;

    private List<RepoData> repoData;
    private GithubApi githubApi;
    private CompositeDisposable compositeDisposable;
    private Callbacks callbacks;
    private RepoAdapter repoAdapter;
    private int pageNumber = 1;
    private PaginationTool<Repo> paginationTool;

    public interface Callbacks {
        void onRepoSelected(String title, String description);
        void onLogoClicked();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        repoData = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
        compositeDisposable.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, parent, false);
        ButterKnife.bind(this, v);
        logo.setOnClickListener(v1 -> {
            if (callbacks != null)
                callbacks.onLogoClicked();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new EndlessScrollImplementation(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                subscribeForData();
            }
        });

        setupAdapter();
        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(getContext()))
                .build();
        githubApi = component.getGithubService();

        subscribeForData();

        return v;
    }


    private void setupAdapter() {
        repoAdapter = new RepoAdapter(getContext(), repoData, callbacks);
        recyclerView.setAdapter(repoAdapter);
    }

    private void subscribeForData() {
        compositeDisposable.add(githubApi.getSquareRepos(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    dataProcessing(data);
                    repoAdapter.notifyItemRangeInserted(30 * pageNumber++, repoData.size());
                })
        );
    }

    private void dataProcessing(List<Repo> repos) {
        for (Repo repo : repos) {
            RepoData repoData = new RepoData();
            repoData.setForks(repo.getForksCount());
            repoData.setStars(repo.getStargazersCount());
            repoData.setName(repo.getName());
            repoData.setDescription(repo.getDescription());
            this.repoData.add(repoData);
        }
    }
}
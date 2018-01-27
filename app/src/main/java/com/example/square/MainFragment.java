package com.example.square;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.square.data.models.commitmodel.RepoData;
import com.example.square.data.models.repomodel.Repo;
import com.example.square.di.components.DaggerSquareComponent;
import com.example.square.di.components.SquareComponent;
import com.example.square.di.modules.ContextModule;
import com.example.square.utils.Adapter;
import com.example.square.utils.EndlessScrollImplementation;
import com.example.square.utils.GithubApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by l1maginaire on 1/26/18.
 */

public class MainFragment extends Fragment {
    private final static String TAG = MainActivity.class.getSimpleName();
    private List<RepoData> list = new ArrayList<>();
    private GithubApi githubApi;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Callbacks mCallbacks;
    private LinearLayoutManager linearLayoutManager;

    private Adapter mAdapter;
    private RecyclerView recyclerView;

    private int pageNumber = 1;

    public interface Callbacks {
        void onRepoSelected(String title, String description);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
        mCompositeDisposable.clear();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.app_name); //todo по умолчанию?
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_recycler, parent, false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView) v.findViewById(R.id.custom_recycler);
        recyclerView.setLayoutManager(linearLayoutManager);// todo: dagger?
        recyclerView.addOnScrollListener(new EndlessScrollImplementation(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                subscribeForData();
            }
        });

        list = new ArrayList<>();
        setupAdapter();

        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(getContext()))
                .build();
        githubApi = component.getGithubService();

        subscribeForData();

        return v;
    }


    private void setupAdapter() {
        mAdapter = new Adapter(getContext(), list, mCallbacks);
        recyclerView.setAdapter(mAdapter);
    }

    private void subscribeForData() {
        mCompositeDisposable.add(githubApi.getSquareRepos(pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
//                        .map(data -> ())
                        .subscribe(data -> {
                            for (Repo repo : data) {
                                RepoData repoData = new RepoData();
                                repoData.setForks(repo.getForksCount());
                                repoData.setStars(repo.getStargazersCount());
                                repoData.setName(repo.getName());
                                repoData.setDescription(repo.getDescription());
                                list.add(repoData);
                            }
                            mAdapter.notifyItemRangeInserted(30 * pageNumber++, list.size());
                        })
        );
    }
}
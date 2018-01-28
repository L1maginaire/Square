package com.example.square.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.square.R;
import com.example.square.data.models.RepoData;
import com.example.square.data.models.repomodel.Repo;
import com.example.square.di.components.DaggerSquareComponent;
import com.example.square.di.components.SquareComponent;
import com.example.square.di.modules.ContextModule;
import com.example.square.utils.RepoAdapter;
import com.example.square.utils.EndlessScrollImplementation;
import com.example.square.interfaces.GithubApi;

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
    private List<RepoData> repoData = new ArrayList<>();
    private GithubApi mGithubApi;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Callbacks mCallbacks;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageView logo;

    private RepoAdapter mRepoAdapter;
    private RecyclerView mRecyclerView;

    private int pageNumber = 1;

    public interface Callbacks {
        void onRepoSelected(String title, String description);

        void onLogoClicked();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, parent, false);
        logo = v.findViewById(R.id.squareLogo);
        logo.setOnClickListener(v1 -> {
            if (mCallbacks != null)
                mCallbacks.onLogoClicked();
        });
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.custom_recycler);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessScrollImplementation(mLinearLayoutManager) {
            @Override
            public void onLoadMore() {
                subscribeForData();
            }
        });

        repoData = new ArrayList<>();
        setupAdapter();
        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(getContext()))
                .build();
        mGithubApi = component.getGithubService();

        subscribeForData();

        return v;
    }


    private void setupAdapter() {
        mRepoAdapter = new RepoAdapter(getContext(), repoData, mCallbacks);
        mRecyclerView.setAdapter(mRepoAdapter);
    }

    private void subscribeForData() {
        mCompositeDisposable.add(mGithubApi.getSquareRepos(pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                            dataProcessing(data);
                            mRepoAdapter.notifyItemRangeInserted(30 * pageNumber++, repoData.size());
                        })
        );
    }

    private void dataProcessing(List<Repo> repos){
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
package com.example.square;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.square.data.models.commitmodel.RepoData;
import com.example.square.data.models.repomodel.Repo;
import com.example.square.di.components.DaggerSquareComponent;
import com.example.square.di.components.SquareComponent;
import com.example.square.di.modules.ContextModule;
import com.example.square.utils.Adapter;
import com.example.square.utils.GithubApi;
import com.squareup.picasso.Picasso;

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
    private CompositeDisposable mCompositeDisposable;
    private Callbacks mCallbacks;

    private Adapter mAdapter;
    private RecyclerView recyclerView;


    public interface Callbacks {
        void onRepoSelected(String title, String description);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.app_name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_recycler, parent, false);
        list = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.custom_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // todo: dagger?

        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(getContext()))
                .build();
        githubApi = component.getGithubService();

        mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(githubApi.getSquareRepos()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
//                        .map(data -> ())
                        .subscribe(data -> {
                            for (Repo repo:data) {
                                RepoData repoData = new RepoData();
                                repoData.setForks(repo.getForksCount());
                                repoData.setStars(repo.getStargazersCount()); //проверить
                                repoData.setName(repo.getName());
                                repoData.setDescription(repo.getDescription());
                                list.add(repoData);
                            }
                            setupAdapter();
                        })
        );
        return v;
    }

    private void setupAdapter() {
        mAdapter = new Adapter(getContext(), list, mCallbacks);
        recyclerView.setAdapter(mAdapter);
    }
}
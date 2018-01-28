package com.example.square.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.square.R;
import com.example.square.data.models.commitmodel.Commit;
import com.example.square.data.models.CommitData;
import com.example.square.di.components.DaggerSquareComponent;
import com.example.square.di.components.SquareComponent;
import com.example.square.di.modules.ContextModule;
import com.example.square.utils.CommitAdapter;
import com.example.square.utils.EndlessScrollImplementation;
import com.example.square.utils.GithubApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CommitsActivity extends AppCompatActivity {
    public static final String REPO_NAME = "repo_name";
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LinearLayoutManager mLinearLayoutManager;
    private CommitAdapter mCommitAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<CommitData> commitList;
    private GithubApi mGithubApi;
    private int pageNumber = 1;
    private final SimpleDateFormat dfFrom = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
    private final SimpleDateFormat dfTo = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    private Date date;

    @Override
    protected void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);

        String repoName = getIntent().getStringExtra(REPO_NAME);

        getSupportActionBar().setSubtitle(repoName);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.commitsRecycler);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessScrollImplementation(mLinearLayoutManager) {
            @Override
            public void onLoadMore() {
                fetchData(repoName);
            }
        });
        commitList = new ArrayList<>();
        setupAdapter();
        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        mGithubApi = component.getGithubService();
        fetchData(repoName);
    }

    private void setupAdapter() {
        mCommitAdapter = new CommitAdapter(this, commitList);
        mRecyclerView.setAdapter(mCommitAdapter);
    }

    private void fetchData(String repoName) {
        mCompositeDisposable.add(mGithubApi.getCommits(repoName, pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
//                        .map(data -> ())
                        .subscribe(data -> {
                            for (Commit commit : data) {
                                CommitData commitData  = new CommitData();
                                commitData.setAuthor(nameFormat(commit.getCommit().getAuthor().getName())); //todo to map
                                commitData.setCommitter(nameFormat(commit.getCommit().getCommitter().getName()));
                                commitData.setDate(dateFormat(commit.getCommit().getAuthor().getDate()));
                                commitData.setMessage(commit.getCommit().getMessage());
                                commitData.setSha(commit.getSha());
                                commitData.setUrl(commit.getHtmlUrl());
                                commitList.add(commitData);
                            }
                            mCommitAdapter.notifyItemRangeInserted(30 * pageNumber++, commitList.size());
                        })
        );
    }

    private String dateFormat(String dateString){
        try {
            date = dfFrom.parse(dateString);
            dateString = dfTo.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    private String nameFormat(String name){
        String[] strings = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s:strings) {
            sb.append(s.substring(0, 1).toUpperCase());
            sb.append(s.substring(1).toLowerCase());
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);//todo
    }
}

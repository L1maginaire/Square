package com.example.square.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.square.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by l1maginaire on 1/26/18.
 */

public class RepoFragment extends Fragment {
    public static final String REPO_ID = "repo_id";
    public static final String REPO_DESCRIPTION = "repo_description";

    @BindView(R.id.repoTitle)
    TextView title;
    @BindView(R.id.repoDescription)
    TextView description;
    @BindView(R.id.commits)
    Button commits;
    @BindView(R.id.contributors)
    Button contributors;
    private Callbacks callbacks;


    public interface Callbacks {
        void onClickCommitButton(String name);
        void onClickContributorsButton(String name);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    public static RepoFragment newInstance(String title, String description) {
        Bundle args = new Bundle();
        args.putSerializable(REPO_ID, title);
        args.putSerializable(REPO_DESCRIPTION, description);
        RepoFragment fragment = new RepoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_repo, container, false);
        ButterKnife.bind(this, v);
        final String repoName = getArguments().getString(REPO_ID);

        title.setText(getArguments().getString(REPO_ID));
        description.setText(getArguments().getString(REPO_DESCRIPTION));
        commits.setOnClickListener(v1 -> callbacks.onClickCommitButton(repoName));
        contributors.setOnClickListener(v2 -> callbacks.onClickContributorsButton(repoName));

        return v;
    }
}

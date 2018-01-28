package com.example.square.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.square.R;

/**
 * Created by l1maginaire on 1/26/18.
 */

public class RepoFragment extends Fragment {
    public static final String REPO_ID = "repo_id";
    public static final String REPO_DESCRIPTION = "repo_description";
    private TextView title;
    private TextView description;
    private Button commits;
    private Button contributors;
    private Callbacks mCallbacks;


    public interface Callbacks {
        void onClickCommitButton(String name);
        void onClickContributorsButton(String name);
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
        final String repoName = getArguments().getString(REPO_ID);
        
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(repoName);

        title = (TextView) v.findViewById(R.id.repoTitle);
        description = (TextView) v.findViewById(R.id.repoDescription);
        commits = (Button) v.findViewById(R.id.commits);
        contributors = (Button) v.findViewById(R.id.contributors);

        title.setText(getArguments().getString(REPO_ID));
        description.setText(getArguments().getString(REPO_DESCRIPTION));
        commits.setOnClickListener(v1 -> mCallbacks.onClickCommitButton(repoName));
        contributors.setOnClickListener(v2 -> mCallbacks.onClickContributorsButton(repoName));

        return v;
    }
}

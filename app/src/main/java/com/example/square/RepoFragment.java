package com.example.square;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        String repoName = getArguments().getString(REPO_DESCRIPTION);
        title = (TextView) v.findViewById(R.id.repoTitle);
        description = (TextView) v.findViewById(R.id.repoDescription);
        commits = (Button) v.findViewById(R.id.commits);

        title.setText(getArguments().getString(REPO_ID));
        description.setText(repoName);
        commits.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), CommitsActivity.class);
            intent.putExtra(CommitsActivity.REPO_NAME, repoName);
            startActivity(intent);
        });

        return v;
    }
}

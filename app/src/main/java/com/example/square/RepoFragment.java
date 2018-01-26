package com.example.square;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by l1maginaire on 1/26/18.
 */

public class RepoFragment extends Fragment {
    private Callbacks mCallbacks;

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
    }

    public static RepoFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        RepoFragment fragment = new RepoFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

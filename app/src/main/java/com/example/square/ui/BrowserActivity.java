package com.example.square.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

public class BrowserActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context, Uri uri) {
        Intent i = new Intent(context, BrowserActivity.class);
        i.setData(uri);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return BrowserFragment.newInstance(getIntent().getData());
    }
}
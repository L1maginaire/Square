package com.example.square.di.modules;

/**
 * Created by l1maginaire on 1/25/18.
 */

import android.app.Activity;
import android.content.Context;

import com.example.square.di.scope.ApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Context context;

    ActivityModule(Activity context) {
        this.context = context;
    }

    @Named("activity_context")
    @ApplicationScope
    @Provides
    public Context context() {
        return context;
    }
}
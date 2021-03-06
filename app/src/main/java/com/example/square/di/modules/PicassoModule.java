package com.example.square.di.modules;

import android.content.Context;

import com.example.square.di.scope.ApplicationScope;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by l1maginaire on 1/25/18.
 */

@Module(includes = OkHttpClientModule.class)
public class PicassoModule {

    @ApplicationScope
    @Provides
    public Picasso picasso(@Named("application_context") Context context, OkHttp3Downloader okHttp3Downloader){
        return new Picasso.Builder(context).
                downloader(okHttp3Downloader).
                build();
    }

    @Provides
    public OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClient){
        return new OkHttp3Downloader(okHttpClient);
    }
}
package com.example.square.di.components;

import com.example.square.di.modules.MainModule;
import com.example.square.di.modules.PicassoModule;
import com.example.square.interfaces.ApplicationScope;
import com.example.square.interfaces.GithubApi;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by l1maginaire on 1/25/18.
 */

@ApplicationScope
@Component(modules = {MainModule.class, PicassoModule.class})
public interface SquareComponent {
    GithubApi getGithubService();
    Picasso getPicasso();
}

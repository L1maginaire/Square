package com.example.square.di.scope;

/**
 * Created by l1maginaire on 1/25/18.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationScope {}
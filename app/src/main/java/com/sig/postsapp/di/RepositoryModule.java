package com.sig.postsapp.di;

import com.sig.postsapp.data.repository.PostRepositoryImpl;
import com.sig.postsapp.domain.repository.PostRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract PostRepository bindPostRepository(PostRepositoryImpl impl);
}
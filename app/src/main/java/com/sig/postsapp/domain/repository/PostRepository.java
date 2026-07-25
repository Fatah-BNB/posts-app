package com.sig.postsapp.domain.repository;

import com.sig.postsapp.domain.model.Post;

import java.util.List;

public interface PostRepository {

    interface Callback<T> {
        void onSuccess(T data);
        void onError(String message);
    }

    void getPosts(Callback<List<Post>> callback);
}
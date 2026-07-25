package com.sig.postsapp.domain.usecase;

import com.sig.postsapp.domain.model.Post;
import com.sig.postsapp.domain.repository.PostRepository;

import java.util.List;

import javax.inject.Inject;

public class GetPostsUseCase {

    private final PostRepository repository;

    @Inject
    public GetPostsUseCase(PostRepository repository) {
        this.repository = repository;
    }

    public void execute(PostRepository.Callback<List<Post>> callback) {
        repository.getPosts(callback);
    }
}
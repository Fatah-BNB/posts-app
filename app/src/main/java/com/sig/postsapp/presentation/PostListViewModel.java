package com.sig.postsapp.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sig.postsapp.domain.model.Post;
import com.sig.postsapp.domain.repository.PostRepository;
import com.sig.postsapp.domain.usecase.GetPostsUseCase;
import com.sig.postsapp.core.Resource;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PostListViewModel extends ViewModel {

    private final GetPostsUseCase getPostsUseCase;
    private final MutableLiveData<Resource<List<Post>>> posts = new MutableLiveData<>();

    @Inject
    public PostListViewModel(GetPostsUseCase getPostsUseCase) {
        this.getPostsUseCase = getPostsUseCase;
        loadPosts();
    }

    public LiveData<Resource<List<Post>>> getPosts() {
        return posts;
    }

    public void loadPosts() {
        posts.setValue(Resource.loading());
        getPostsUseCase.execute(new PostRepository.Callback<>() {
            @Override
            public void onSuccess(List<Post> data) {
                posts.setValue(Resource.success(data));
            }

            @Override
            public void onError(String message) {
                posts.setValue(Resource.error(message));
            }
        });
    }
}
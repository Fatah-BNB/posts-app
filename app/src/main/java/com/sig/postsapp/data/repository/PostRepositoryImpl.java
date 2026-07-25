package com.sig.postsapp.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.sig.postsapp.R;
import com.sig.postsapp.data.remote.ApiService;
import com.sig.postsapp.data.remote.PostDto;
import com.sig.postsapp.data.remote.PostMapper;
import com.sig.postsapp.domain.model.Post;
import com.sig.postsapp.domain.repository.PostRepository;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Response;

public class PostRepositoryImpl implements PostRepository {

    private final ApiService apiService;
    private final Context context;

    @Inject
    public PostRepositoryImpl(ApiService apiService, @ApplicationContext Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    @Override
    public void getPosts(Callback<List<Post>> callback) {
        apiService.getPosts().enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<PostDto>> call, @NonNull Response<List<PostDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(PostMapper.toDomainList(response.body()));
                } else {
                    callback.onError(context.getString(R.string.error_server, response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PostDto>> call, @NonNull Throwable t) {
                callback.onError(t instanceof IOException
                        ? context.getString(R.string.error_network)
                        : context.getString(R.string.error_unknown));
            }
        });
    }
}
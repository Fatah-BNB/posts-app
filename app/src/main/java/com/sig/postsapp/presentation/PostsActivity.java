package com.sig.postsapp.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sig.postsapp.R;
import com.sig.postsapp.core.Constants;
import com.sig.postsapp.core.Resource;
import com.sig.postsapp.databinding.ActivityPostsBinding;
import com.sig.postsapp.domain.model.Post;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostsActivity extends AppCompatActivity {
    private ActivityPostsBinding binding;
    private PostListViewModel viewModel;
    private PostAdapter adapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences(Constants.AUTH_PREFS_NAME, MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(PostListViewModel.class);

        adapter = new PostAdapter(post -> {
            // Handle post click if needed
        });

        binding.recyclerPosts.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerPosts.setAdapter(adapter);

        binding.btnRetry.setOnClickListener(v -> viewModel.loadPosts());
        binding.btnLogout.setOnClickListener(v -> logout());

        viewModel.getPosts().observe(this, this::render);
    }

    private void render(Resource<List<Post>> resource) {
        binding.progressBar.setVisibility(resource.status == Resource.Status.LOADING ? View.VISIBLE : View.GONE);
        binding.layoutError.setVisibility(resource.status == Resource.Status.ERROR ? View.VISIBLE : View.GONE);
        binding.recyclerPosts.setVisibility(resource.status == Resource.Status.SUCCESS ? View.VISIBLE : View.GONE);

        if (resource.status == Resource.Status.SUCCESS) {
            adapter.setPosts(resource.data);
        } else if (resource.status == Resource.Status.ERROR) {
            binding.txtError.setText(resource.message);
        }
    }

    private void logout() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.logout_confirmation_title)
                .setMessage(R.string.logout_confirmation_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    sharedPreferences.edit().putBoolean(Constants.IS_LOGGED_IN, false).apply();
                    startActivity(new Intent(this, LoginActivity.class));
                    finishAffinity();
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
}
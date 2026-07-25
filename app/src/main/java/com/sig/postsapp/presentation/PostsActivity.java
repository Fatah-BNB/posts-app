package com.sig.postsapp.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sig.postsapp.R;
import com.sig.postsapp.core.Constants;
import com.sig.postsapp.databinding.ActivityPostsBinding;
import com.sig.postsapp.domain.model.Post;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostsActivity extends AppCompatActivity implements PostAdapter.OnPostClickListener {
    private ActivityPostsBinding binding;
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

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    if (sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, false)) {
                        finishAffinity();
                    } else {
                        startActivity(new Intent(PostsActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PostListFragment())
                    .commit();
        }
    }

    @Override
    public void onPostClick(Post post) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                )
                .replace(R.id.fragment_container, PostDetailFragment.newInstance(post))
                .addToBackStack(null)
                .commit();
    }
}
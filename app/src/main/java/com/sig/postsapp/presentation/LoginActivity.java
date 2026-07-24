package com.sig.postsapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sig.postsapp.R;
import com.sig.postsapp.core.Constants;
import com.sig.postsapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences = getSharedPreferences(Constants.AUTH_PREFS_NAME, Context.MODE_PRIVATE);

        binding.btnLogin.setOnClickListener(view -> login(binding.etEmailUsername.getText().toString().trim(), binding.etPassword.getText().toString().trim()));
    }

    private void login(String emailOrUsername, String password) {
        if (emailOrUsername.isEmpty()) {
            binding.tilEmailUsername.setError(getString(R.string.email_or_username_required));
            return;
        }
        if (password.isEmpty()) {
            binding.tilPassword.setError(getString(R.string.password_required));
            return;
        }

        // Show loading state
        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText(""); // Clear text to show spinner clearly
        binding.progressBar.setVisibility(View.VISIBLE);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            sharedPreferences.edit().putBoolean(Constants.IS_LOGGED_IN, true).apply();
            startActivity(new Intent(this, PostsActivity.class));
            finish();
        }, 2000);
    }
}
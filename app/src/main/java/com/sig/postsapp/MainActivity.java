package com.sig.postsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sig.postsapp.core.Constants;
import com.sig.postsapp.databinding.ActivityMainBinding;
import com.sig.postsapp.presentation.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.AUTH_PREFS_NAME, Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        }
//        else {
//            startActivity(new Intent(this, HomeActivity.class));
//            finishAffinity();
//        }


        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
package com.sig.postsapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sig.postsapp.R;
import com.sig.postsapp.core.Constants;
import com.sig.postsapp.core.Resource;
import com.sig.postsapp.databinding.FragmentPostListBinding;
import com.sig.postsapp.domain.model.Post;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostListFragment extends Fragment implements PostAdapter.OnPostClickListener {

    private FragmentPostListBinding binding;
    private PostListViewModel viewModel;
    private PostAdapter adapter;
    private SharedPreferences sharedPreferences;
    private PostAdapter.OnPostClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PostAdapter.OnPostClickListener) {
            listener = (PostAdapter.OnPostClickListener) context;
        } else {
            throw new RuntimeException(context + " must implement PostAdapter.OnPostClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPostListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireContext().getSharedPreferences(Constants.AUTH_PREFS_NAME, Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(PostListViewModel.class);

        adapter = new PostAdapter(this);

        binding.recyclerPosts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerPosts.setAdapter(adapter);

        binding.btnRetry.setOnClickListener(v -> viewModel.loadPosts());
        binding.btnLogout.setOnClickListener(v -> logout());

        viewModel.getPosts().observe(getViewLifecycleOwner(), this::render);
    }

    @Override
    public void onPostClick(Post post) {
        if (listener != null) {
            listener.onPostClick(post);
        }
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
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.logout_confirmation_title)
                .setMessage(R.string.logout_confirmation_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    sharedPreferences.edit().putBoolean(Constants.IS_LOGGED_IN, false).apply();
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                    requireActivity().finishAffinity();
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
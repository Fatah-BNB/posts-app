package com.sig.postsapp.data.remote;

import com.sig.postsapp.domain.model.Post;

import java.util.ArrayList;
import java.util.List;

public final class PostMapper {

    private PostMapper() {
    }

    public static List<Post> toDomainList(List<PostDto> dtos) {
        List<Post> posts = new ArrayList<>(dtos.size());
        for (PostDto dto : dtos) {
            posts.add(new Post(dto.getId(), dto.getTitle(), dto.getBody()));
        }
        return posts;
    }
}
package com.sig.postsapp.domain.model;

import java.io.Serializable;

public class Post implements Serializable {

    private final int id;
    private final String title;
    private final String body;

    public Post(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
}
package com.framework.study.dto;


import com.framework.study.domain.Post;
import lombok.Getter;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String createdAt;
    private String modifiedAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt().toString();
        this.modifiedAt = post.getModifiedAt() != null ? post.getModifiedAt().toString() : null;
    }
}
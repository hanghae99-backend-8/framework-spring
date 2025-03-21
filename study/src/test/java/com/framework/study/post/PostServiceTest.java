package com.framework.study.post;

import com.framework.study.domain.Post;
import com.framework.study.dto.PostRequest;
import com.framework.study.dto.PostResponse;
import com.framework.study.jwt.JWTUtil;
import com.framework.study.repository.PostRepository;
import com.framework.study.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post(null, "Test Title", "Test Content", "password123");
    }

    @Test
    void createPost() {
        when(postRepository.save(any(Post.class))).thenReturn(post);
        PostRequest request = new PostRequest();
        PostResponse response = postService.createPost("",request);
        assertEquals("Test Title", response.getTitle());
    }

    @Test
    void getPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        var response = postService.getPostById(1L);
        assertEquals("Test Title", response.getTitle());
    }

    @Test
    void updatePost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        PostRequest request = new PostRequest();
        var response = postService.updatePost(1L, request);
        assertEquals("Test Title", response.getTitle());
    }

    @Test
    void deletePost() {
        doNothing().when(postRepository).deleteById(1L);
        assertDoesNotThrow(() -> postService.deletePost(1L));
    }
}

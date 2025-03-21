package com.framework.study.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.framework.study.service.PostService;
import com.framework.study.domain.Post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    
    @GetMapping("")
    public String getPostmain() {
        return "post";
    }

    // 모든 게시글 조회
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }

    // ID로 게시글 조회
    // {id} 경로 변수를 통해 특정 게시글의 ID를 받습니다 
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        // postService.findPostById()를 통해 해당 ID의 게시글을 조회합니다
        Optional<Post> post = postService.findPostById(id);
        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } else {
            // 게시글이 존재하지 않으면 404 Not Found를 반환합니다
            return ResponseEntity.notFound().build();
        }
    }


    // 검색 기능 (제목, 내용, 또는 둘 다)
    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam(required = false) String title) {
        
        if (title != null) {
            // 제목으로 검색
            return ResponseEntity.ok(postService.findPostsByTitleKeyword(title));
        } else {
            // 검색 매개변수가 없으면 400 상태 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

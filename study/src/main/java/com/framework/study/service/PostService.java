package com.framework.study.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.framework.study.domain.Post;
import com.framework.study.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 모든 게시글 조회
    @Transactional
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    // ID로 게시글 조회
    @Transactional
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    // 키워드로 제목 검색
    @Transactional
    public List<Post> findPostsByTitleKeyword(String keyword) {
        return postRepository.findByTitleContaining(keyword);
    }

    // 게시글 조회수 증가
    @Transactional
    public void increasePostViewCount(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.increaseViewCount();
            postRepository.save(post);
        }
    }
    
}

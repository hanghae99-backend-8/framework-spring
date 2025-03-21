package com.framework.study.service;

import com.framework.study.domain.Member;
import com.framework.study.domain.Post;
import com.framework.study.dto.PostRequest;
import com.framework.study.dto.PostResponse;
import com.framework.study.jwt.JWTUtil;
import com.framework.study.repository.MemberRepository;
import com.framework.study.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private MemberRepository memberRepository;
    private JWTUtil jwtUtil;

    public PostService(PostRepository postRepository, JWTUtil jwtUtil, MemberRepository memberRepository){
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public PostResponse createPost(String token, PostRequest request) {
//        유저 체크
        String username = jwtUtil.getUsername(token);
        Member member = memberRepository.findByUsername(username);

//        게시글 생성
        Post post = new Post(member, request.getTitle(), request.getContent(), request.getPostPassword());
        return new PostResponse(postRepository.save(post));
    }

    public PostResponse getPostById(Long id) {
        return postRepository.findById(id).map(PostResponse::new)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        return postRepository.findById(id)
                .map(post -> {
                    post.update(request.getTitle(), request.getContent());
                    return new PostResponse(postRepository.save(post));
                })
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}

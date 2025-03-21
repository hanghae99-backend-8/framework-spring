package com.framework.study.repository;

import com.framework.study.domain.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 제목 포함 검색
    List<Post> findByTitleContaining(String keyword);
}

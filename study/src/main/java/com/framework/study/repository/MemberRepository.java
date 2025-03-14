package com.framework.study.repository;

import com.framework.study.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByUsername(String username);
    Member findByUsername(String username);
}

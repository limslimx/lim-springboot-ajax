package com.lim.springboot.test.service;

import com.lim.springboot.test.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String userEmail);

    Optional<Member> findByuId(String uId);

    @Query("select m.uId from Member m where m.email=:email")
    Optional<String> findByUserEmail(@Param("email") String email);

    @Query("select count(m) from Member m where m.uId=:uId")
    int countMemberByUId(@Param("uId") String uId);

    @Query("select count(m) from Member m where m.email=:email")
    int countMemberByEmail(@Param("email") String email);
}

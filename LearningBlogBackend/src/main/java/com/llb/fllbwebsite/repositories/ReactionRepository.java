package com.llb.fllbwebsite.repositories;

import com.llb.fllbwebsite.domain.Post;
import com.llb.fllbwebsite.domain.Reaction;
import com.llb.fllbwebsite.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Long> {
    Reaction findByUserAndPost(User user, Post post);
    //void deleteByUserAndPost(User user, Post post);
}

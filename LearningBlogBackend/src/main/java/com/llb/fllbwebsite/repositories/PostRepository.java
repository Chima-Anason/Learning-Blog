package com.llb.fllbwebsite.repositories;

import com.llb.fllbwebsite.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    Optional<Post> findById(Long postId);

    @Query("SELECT p from Post p where lower(p.content) like %?1% or lower(p.title) like %?1% order by p.createdAt")
    List<Post> findByContentOrTitleIgnoreLetterCase(String searchText);


//    Post findByTitle(String postTitle);
}

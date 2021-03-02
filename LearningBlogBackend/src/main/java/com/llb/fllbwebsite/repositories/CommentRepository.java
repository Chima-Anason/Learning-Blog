package com.llb.fllbwebsite.repositories;

import com.llb.fllbwebsite.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {


}

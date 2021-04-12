package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.Comment;
import com.llb.fllbwebsite.domain.Post;
import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.exceptions.PostNotFoundException;
import com.llb.fllbwebsite.exceptions.PostTitleException;
import com.llb.fllbwebsite.exceptions.UserIdException;
import com.llb.fllbwebsite.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Comment saveOrUpdateComment(Comment comment, Long postId, String username){
        try {
            //check if user exist
            User user = userService.findUserByUsername(username);
            //find the post
            Post post = postService.findPostById(postId);
            //set relationship attributes
            comment.setPost(post);
            comment.setUser(user);
            comment.setUserName(user.getUsername());
            comment.setPostName(post.getTitle());
            //save into Or update the  database
            return  commentRepository.save(comment);
        }catch (PostNotFoundException | UserIdException e) {
            throw e;
        }

    }


    public Iterable<Comment> findAllComments(){
        return commentRepository.findAll();
    }
}

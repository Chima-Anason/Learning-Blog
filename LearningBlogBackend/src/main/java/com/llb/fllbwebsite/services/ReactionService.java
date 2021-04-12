package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.Post;
import com.llb.fllbwebsite.domain.Reaction;
import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.exceptions.PostNotFoundException;
import com.llb.fllbwebsite.exceptions.PostTitleException;
import com.llb.fllbwebsite.exceptions.ReactionNotFoundException;
import com.llb.fllbwebsite.exceptions.UserIdException;
import com.llb.fllbwebsite.repositories.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public ReactionService(ReactionRepository reactionRepository, PostService postService, UserService userService) {
        this.reactionRepository = reactionRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Reaction saveLike(Long postId, String username,Reaction reaction){
        try{
            //check if user exist
            User user = userService.findUserByUsername(username);
            //find the post
            Post post = postService.findPostById(postId);

            //check if the user already liked the post
            Reaction userReaction = reactionRepository.findByUserAndPost(user,post);
            if(userReaction != null){
                return null;
            }
            //set relationship attribute
            reaction.setUser(user);
            reaction.setUserName(user.getUsername());
            reaction.setPost(post);
            reaction.setPostName(post.getTitle());

            //save into the  database
            return reactionRepository.save(reaction);
        }catch (UserIdException | PostNotFoundException e){
            throw e;
        }
    }

    public void deleteLike(Long postId, String username){
        try{
            //check if user exist
            User user = userService.findUserByUsername(username);
            //find the post
            Post post = postService.findPostById(postId);
            //find user's reaction
            Reaction userReaction = reactionRepository.findByUserAndPost(user, post);
            //save into the  database
            reactionRepository.delete(userReaction);

        }catch (UserIdException | PostNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new ReactionNotFoundException("user like for this post was not found in the database");
        }
    }

    public Iterable<Reaction> findAllPostLikes(){
        return reactionRepository.findAll();
    }


}

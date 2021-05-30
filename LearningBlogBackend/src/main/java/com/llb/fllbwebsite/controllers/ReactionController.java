package com.llb.fllbwebsite.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.llb.fllbwebsite.domain.Reaction;
import com.llb.fllbwebsite.domain.Views;
import com.llb.fllbwebsite.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/post")
public class ReactionController {

    private final ReactionService reactionService;

    @Autowired
    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }


    // Create Like [ @route: /api/post/postId/like  @access: private]
    @PostMapping("/{postId}/like")
    public ResponseEntity<Reaction> createLike(@PathVariable Long postId, Principal principal, @RequestBody Reaction reaction){
        Reaction newReaction = reactionService.saveLike(postId, principal.getName(), reaction);
        return  new ResponseEntity<>(newReaction, HttpStatus.CREATED);
    }

    // Unlike post [ @route: /api/post/postId/unlike  @access: private]
    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<String> removeLike(@PathVariable Long postId, Principal principal){
        reactionService.deleteLike(postId, principal.getName());
        return  new ResponseEntity<>("post '" + postId + "' successfully unliked ", HttpStatus.OK);
    }


    // Get all Reactions  { @route: api/post/likes/all,  access: public }
    @GetMapping("/likes/all")
    @JsonView(Views.External.class)
    public ResponseEntity<Iterable<Reaction>> getAllLikes(){
        return new ResponseEntity<>(reactionService.findAllPostLikes(),HttpStatus.OK) ;
    }
}

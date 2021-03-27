package com.llb.fllbwebsite.controllers;


import com.llb.fllbwebsite.domain.Reaction;
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

    // Create Like  { @route: api/post/postTitle/like,  access: private }
    @PostMapping("/{postTitle}/like")
    public ResponseEntity<Reaction> createLike(@PathVariable String postTitle, Principal principal, @RequestBody Reaction reaction){
        Reaction newReaction = reactionService.saveLike(postTitle, principal.getName(), reaction);
        return new ResponseEntity<>(newReaction, HttpStatus.CREATED);

    }

    // unlike post  { @route: api/post/postTitle/unlike,  access: private }
    @DeleteMapping("/{postTitle}/unlike")
    public ResponseEntity<String>  removeLike(@PathVariable String postTitle,Principal principal){
        reactionService.deleteLike(postTitle,principal.getName());
        return new ResponseEntity<>("post '" + postTitle + "' successfully unlike " , HttpStatus.OK);
    }


    // Get all Reactions  { @route: api/post/likes/all,  access: public }
    @GetMapping("/likes/all")
    public ResponseEntity<Iterable<Reaction>> getAllLikes(){
        return new ResponseEntity<>(reactionService.findAllPostLikes(),HttpStatus.OK) ;
    }
}

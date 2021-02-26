package com.llb.fllbwebsite.controllers;


import com.llb.fllbwebsite.domain.Post;
import com.llb.fllbwebsite.services.PostService;
import com.llb.fllbwebsite.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final ValidationErrorService validationErrorService;

    @Autowired
    public PostController(PostService postService, ValidationErrorService validationErrorService) {
        this.postService = postService;
        this.validationErrorService = validationErrorService;
    }

    // Create post  { @route: api/posts,  access: private }
    @PostMapping("")
    public ResponseEntity<?> createPost(@Valid @RequestBody Post post, BindingResult result){

        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if(errorMap!= null) return errorMap;

        Post newPost = postService.saveOrUpdatePost(post);
        return new ResponseEntity<Post>(newPost, HttpStatus.CREATED);
    }

    // Get all post  { @route: api/posts/all,  access: public }
    @GetMapping("/all")
    public ResponseEntity<Iterable<Post>> getAllPosts(){
        return new ResponseEntity<Iterable<Post>>(postService.findAllPosts(), HttpStatus.OK);
    }

    // Get post by ID { @route: api/posts/:id,  access: private/public }
    @GetMapping("/id/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId){
        Optional<Post> post = postService.findPostById(postId);
        return new ResponseEntity<Optional<Post>>(post, HttpStatus.OK);
    }

    // Delete post by ID  { @route: api/posts/:id,  access: private }
    @DeleteMapping("/id/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable Long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<String>("Post with ID '" + postId + "' was deleted", HttpStatus.OK);
    }

    // Get post by Title or Content { @route: api/posts/search?searchText=value,  access: private/public }
    @GetMapping("/search")
    public ResponseEntity<?> searchPostByTitleOrContent(@RequestParam(value = "searchText") String searchText){
        List<Post> foundPosts = postService.findByContentOrTitleIgnoreLetterCase(searchText);
        return new ResponseEntity<>(foundPosts, HttpStatus.OK);
    }

    // use Lodash to remove space from the String passed on the pathvariable
    // Get post by Title { @route: api/posts/:id,  access: private/public }
//    @GetMapping("/title/{postTitle}")
//    public ResponseEntity<?> getPostByTitle(@PathVariable String postTitle){
//        postTitle = postTitle.replace("-", " ");
//        Post post = postService.findPostByTitle(postTitle);
//        return new ResponseEntity<Post>(post, HttpStatus.OK);
//    }



}

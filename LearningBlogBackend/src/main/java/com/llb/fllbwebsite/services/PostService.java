package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.Post;
import com.llb.fllbwebsite.exceptions.PostNotFoundException;
import com.llb.fllbwebsite.exceptions.PostTitleException;
import com.llb.fllbwebsite.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService  {

    private final PostRepository postRepository;


    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post saveOrUpdatePost(Post post){
        try{

            return postRepository.save(post);
        }catch(Exception e){
            throw new PostTitleException("Post title '"  + post.getTitle() + "' already exist");
        }
    }

    public Iterable<Post> findAllPosts(){
        return postRepository.findAll();
    }


    public Optional<Post> findPostById(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        if(!post.isPresent()){
            throw new PostNotFoundException("Post Id '" + postId + "' don't exist");
        }
        return post;
    }

    public List<Post> findByContentOrTitleIgnoreLetterCase(String searchText){
        List<Post> posts = postRepository.findByContentOrTitleIgnoreLetterCase(searchText);

        if(posts.size() < 1){

            throw new PostNotFoundException("Search result not found");

        }
        return posts;
    }

    public void deletePostById(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        if(!post.isPresent()){
            throw new PostNotFoundException("Cannot delete, Post Id '" + postId + "' don't exist");
        }
        postRepository.deleteById(postId);
    }

//    public Post findPostByTitle(String postTitle){
//        return postRepository.findByTitle(postTitle);
//    }

}

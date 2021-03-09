package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.Category;
import com.llb.fllbwebsite.domain.Post;
import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.exceptions.CategoryNameException;
import com.llb.fllbwebsite.exceptions.PostNotFoundException;
import com.llb.fllbwebsite.exceptions.PostTitleException;
import com.llb.fllbwebsite.exceptions.UserIdException;
import com.llb.fllbwebsite.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService  {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CategoryService categoryService;


    @Autowired
    public PostService(PostRepository postRepository, UserService userService, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public Post saveOrUpdatePost(Post post, String userEmail){
        try{
            //find user and set relationship with post
            User user = userService.findUserByEmail(userEmail);
            post.setUser(user);
            post.setAuthor(user.getUsername());

            //find category and set relationship with post
            Category category = categoryService.findCategoryByName(post.getCategoryName());
            post.setCategory(category);
            return postRepository.save(post);
        }catch(UserIdException | CategoryNameException e){
            throw e;
        } catch (Exception e){
            throw new PostTitleException("Post title '"  + post.getTitle() + "' has been used");
        }
    }

    public Iterable<Post> findAllPosts(){
        return postRepository.findAll();
    }


    public Optional<Post> findPostById(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        if(!post.isPresent()){
            throw new PostNotFoundException("Post with Id '" + postId + "' don't exist");
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
            throw new PostNotFoundException("Cannot delete! Post with Id '" + postId + "' don't exist");
        }
        postRepository.deleteById(postId);
    }

    public Post findPostByTitle(String postTitle){
        Post post = postRepository.findByTitle(postTitle);
        if(post == null){
            throw new PostTitleException("Post with '" + postTitle + "' does not exist");
        }
        return post;
    }

}

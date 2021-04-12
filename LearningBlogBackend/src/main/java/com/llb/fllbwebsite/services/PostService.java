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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.llb.fllbwebsite.security.SecurityConstants.SUPER_ADMIN_ROLE;

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

    public void postDontExistMessage(Post post, String message){
        if (post == null) {
            throw new PostNotFoundException(message);
        }
    }

    public Post saveOrUpdatePost(Post post, String username){
        try{
            //find user and set relationship with post
            User user = userService.findUserByUsername(username);
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
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }


    public Post findPostById(Long postId){
        Post post = postRepository.getById(postId);
        postDontExistMessage(post, "Post with Id '" + postId + "' don't exist");
        return post;
    }

    public Iterable<Post> findAllPostsByUser(String username){
        User user = userService.findUserByUsername(username);
        return postRepository.findAllByUserOrderByIdDesc(user);
    }

    public List<Post> findPostByContentOrTitleIgnoreLetterCase(String searchText){
        List<Post> posts = postRepository.findPostByContentOrTitleIgnoreLetterCase(searchText);
        if (posts.size() < 1){

            throw new PostNotFoundException("Search result not found");

        }
        return posts;
    }

    public void deletePostById(Long postId, String username){
        User authenticatedUser = userService.findUserByUsername(username);
        String authenticatedUserRole = authenticatedUser.getRole().getRoleName();
        Post post = findPostById(postId);
        User postOwner = post.getUser();

        if (!postOwner.equals(authenticatedUser) && !authenticatedUserRole.equals(SUPER_ADMIN_ROLE)){
            throw new UserIdException("You are not allowed to delete this post");
        }
        postRepository.delete(post);
    }

    public Post findPostByTitle(String postTitle){
        Post post = postRepository.findByTitle(postTitle);
        postDontExistMessage(post, "Post with title '" + postTitle + "' does not exist");
        return post;
    }

}

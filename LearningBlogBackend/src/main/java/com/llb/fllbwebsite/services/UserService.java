package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.exceptions.UserIdException;
import com.llb.fllbwebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        // save conditions
        try{
            return userRepository.save(user);
        }catch (Exception e){
            throw new UserIdException("User already exist");
        }


    }

    public User updateUser(User updateUser, Long userId){
        //find the  user by userId
        User foundUser = userRepository.getById(userId);
        if (foundUser == null){
            throw new UserIdException("User with Id '" + userId + "' does not exist");
        }

        //replace the user
        foundUser = updateUser;

        //save the user
        return userRepository.save(foundUser);
    }

    public Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long userId){
        Optional<User> user = userRepository.findById(userId);
        // find condition
        if(!user.isPresent()){
            throw new UserIdException("User with Id '" + userId + "' does not exist");
        }
        return user;
    }

    public User findUserByEmail(String userEmail){
        User user = userRepository.findByEmail(userEmail);
        // find condition
        if(user == null){
            throw new UserIdException("User with email '" + userEmail + "' does not exist");
        }
        return user;
    }

    public void deleteUserById(Long userId){
        Optional<User> user = userRepository.findById(userId);
        //delete Condition
        if(!user.isPresent()){
            throw new UserIdException("Cannot delete: User with Id '" + userId + "' does not exist");
        }
        userRepository.deleteById(userId);
    }
}

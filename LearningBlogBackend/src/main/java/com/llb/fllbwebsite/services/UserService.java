package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.exceptions.UserIdException;
import com.llb.fllbwebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveOrUpdateUser(User user){
        // save conditions
        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            //Username has to be unique (using the UserIdException)

            // Make sure the password and the confirmPassword match
            //password and confirmPassword must match (using the UserValidator class)

            // we don't persist or show the confirmPassword
            user.setConfirmPassword("");
            return userRepository.save(user);
        }catch (Exception e){
            throw new UserIdException("User already exist");
        }


    }

    public Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findUserById(Long userId){
        User user = userRepository.getById(userId);
        // find condition
        userDontExistMessage(user, "User with Id '" + userId + "' dose not exist");
        return user;
    }

    public User findUserByEmail(String userEmail){
        User user = userRepository.findByEmail(userEmail);
        userDontExistMessage(user, "User with email '" + userEmail + "' dose not exist");
        return user;
    }

    public User findUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        userDontExistMessage(user, "User with username '" + username + "' dose not exist");
        return user;
    }

    public void deleteUserById(Long userId){
        //called the method findUserById which is in this class(service)
        User user = findUserById(userId);
        userRepository.delete(user);
    }

    public void userDontExistMessage(User user, String message){
        if (user == null) {
            throw new UserIdException(message);
        }
    }
}

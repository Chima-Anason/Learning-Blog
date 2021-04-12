package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.exceptions.RoleNotFoundException;
import com.llb.fllbwebsite.exceptions.UserIdException;
import com.llb.fllbwebsite.payload.UserUpdateRequest;
import com.llb.fllbwebsite.repositories.RoleRepository;
import com.llb.fllbwebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.llb.fllbwebsite.security.SecurityConstants.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User save(User user){
        // save conditions
        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            //Username has to be unique (using the UserIdException)

            // Make sure the password and the confirmPassword match
            //password and confirmPassword must match (using the UserValidator class)

            //confirmPassword shouldn't be persisted or shown
            user.setConfirmPassword("");

            // assign user a role------ calls the assignRole method
            this.assignRole(user);

            //save
            return userRepository.save(user);
        }catch (RoleNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new UserIdException("User already exist");
        }
    }

    public User updateUser(UserUpdateRequest userUpdateRequest, Long userId, String username){

        Long userUpdateRequestId = userUpdateRequest.getId();
        User authenticatedUser = findUserByUsername(username);
        String userRole = authenticatedUser.getRole().getRoleName();
        User storedUser = findUserById(userId);

        if(!userUpdateRequestId.equals(userId)){
            throw new UserIdException("Update ID does not match the Id on this request path");
        }
        if(!authenticatedUser.equals(storedUser) && !userRole.equals(SUPER_ADMIN_ROLE)){
            throw new UserIdException("You are not allowed to update this user");
        }


        // calls the mapUser function
        mapUser(userUpdateRequest, storedUser);
        // updates the database
        return userRepository.save(storedUser);

    }

    protected void assignRole(User user){

        if(!user.getRoleName().equals(SUPER_ADMIN_ROLE) && !user.getRoleName().equals(SUB_ADMIN_ROLE)){
            user.setRoleName(DEFAULT_USER_ROLE);
        }

        user.setRole(roleService.findRoleByName(user.getRoleName()));
    }

    // sets user old information to the updated information
    protected void mapUser(UserUpdateRequest updatedInfo, User oldInfo){

        oldInfo.setUsername(updatedInfo.getUsername());
        oldInfo.setEmail(updatedInfo.getEmail());
        oldInfo.setPhoneNumber(updatedInfo.getPhoneNumber());
        oldInfo.setAvatarImg(updatedInfo.getAvatarImg());
        oldInfo.setRole(roleService.findRoleByName(updatedInfo.getRoleName()));
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

package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.exceptions.UserIdException;
import com.llb.fllbwebsite.repositories.RoleRepository;
import com.llb.fllbwebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.llb.fllbwebsite.security.SecurityConstants.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveOrUpdateUser(User user){
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
        }catch (Exception e){
            throw new UserIdException("User already exist");
        }
    }

    public void assignRole(User user){

        if(!user.getRoleName().equals(SUPER_ADMIN_ROLE) && !user.getRoleName().equals(SUB_ADMIN_ROLE)){
            user.setRoleName(DEFAULT_USER_ROLE);
        }
        user.setRole(roleRepository.findByRoleName(user.getRoleName()));

        //        Role newRole;
//        if (user.getRoleName().equals(SUPER_ADMIN_ROLE)){
//            newRole = roleRepository.findByRoleName(SUPER_ADMIN_ROLE);   // superAdmin role
//        }else if (user.getRoleName().equals(SUB_ADMIN_ROLE)){
//            newRole = roleRepository.findByRoleName(SUB_ADMIN_ROLE);    // subAdmin role
//        }else newRole = roleRepository.findByRoleName(DEFAULT_USER_ROLE);    // user role (by default)
//
//        user.setRole(newRole);
    }

//    if (user.getRoleName().equals("") || user.getRoleName().equals(DEFAULT_USER_ROLE)){
//        Role newRole= roleRepository.findByRole_name(DEFAULT_USER_ROLE);
//        user.setRole(newRole);
//    }
//
//           if (user.getRoleName().equals(SUB_ADMIN_ROLE)){
//        Role newRole= roleRepository.findByRole_name(SUB_ADMIN_ROLE);
//        user.setRole(newRole);
//    }
//
//           if (user.getRoleName().equals(SUPER_ADMIN_ROLE)){
//        Role newRole = roleRepository.findByRole_name(SUPER_ADMIN_ROLE);
//        user.setRole(newRole);
//    }


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

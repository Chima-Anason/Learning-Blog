package com.llb.fllbwebsite.controllers;

import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.services.UserService;
import com.llb.fllbwebsite.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ValidationErrorService validationErrorService;

    @Autowired
    public UserController(UserService userService, ValidationErrorService validationErrorService) {
        this.userService = userService;
        this.validationErrorService = validationErrorService;
    }

    // Create user  { @route: api/users,  @access: private }
    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result){
        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;
        User newUser = userService.saveUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    //Update User { @route: api/users/id/:id  @access: private }
    @PatchMapping("/id/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @Valid @RequestBody User user, BindingResult result){
        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if (errorMap != null) return  errorMap;

        User updateUser = userService.updateUser(user, userId);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    // Get all user  { @route: api/users/all,  access: public }
    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
    }

    // Get user by ID { @route: api/users/:id,  @access: private/public }
    @GetMapping("/id/{userId}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long userId){
        Optional<User> user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Delete user by ID  { @route: api/users/:id,  @access: private }
    @DeleteMapping("/id/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>("User with ID '" + userId + "' was deleted successfully", HttpStatus.OK);
    }
}

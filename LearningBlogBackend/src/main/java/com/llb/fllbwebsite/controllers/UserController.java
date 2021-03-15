package com.llb.fllbwebsite.controllers;

import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.services.UserService;
import com.llb.fllbwebsite.services.ValidationErrorService;
import com.llb.fllbwebsite.validator.UserValidator;
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
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, ValidationErrorService validationErrorService, UserValidator userValidator) {
        this.userService = userService;
        this.validationErrorService = validationErrorService;
        this.userValidator = userValidator;
    }

    // Create user  { @route: api/users,  access: private }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        // Validate password match
        userValidator.validate(user, result);
        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;
        User newUser = userService.saveOrUpdateUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Get all user  { @route: api/users/all,  access: public }
    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
    }

    // Get user by ID { @route: api/users/:id,  access: private/public }
    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        User user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Delete user by ID  { @route: api/users/:id,  access: private }
    @DeleteMapping("/id/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<String>("User with ID '" + userId + "' was deleted successfully", HttpStatus.OK);
    }
}

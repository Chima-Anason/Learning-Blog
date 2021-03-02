package com.llb.fllbwebsite.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.llb.fllbwebsite.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Firstname is required")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    private String lastName;


    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @Email(message = "Please input a valid email address")
    @NotBlank(message = "Email address is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password field is required")
    @Size(min = 5, max = 11, message = "password must be more than 5 characters")
    private String password;

    @Transient
    private String confirmPassword;

    @NotBlank(message = "Phone number is required")
    @Size(min = 11, max = 11, message = "Invalid mobile number")
    private String phoneNumber;
    private String avatarImg;

    //One-to-many relationship with Post
    @OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Post> posts;



    //One-to-Many relationship with Comments
    @OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments;

    //Role
    //Reaction
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registered_At;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated_At;

    @PrePersist
    public void onRegister(){
        this.registered_At = new Date();
    }

    @PreUpdate
    public void onUpdate(){
        this.updated_At = new Date();
    }


}

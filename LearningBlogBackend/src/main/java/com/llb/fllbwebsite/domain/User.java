package com.llb.fllbwebsite.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.llb.fllbwebsite.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.External.class)
    private Long id;

    @NotBlank(message = "Firstname is required")
    @JsonView(Views.External.class)
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @JsonView(Views.External.class)
    private String lastName;

    @Transient
    private String fullName;


    @Enumerated(EnumType.STRING)
    @JsonView(Views.External.class)
    private Gender gender;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    @JsonView(Views.External.class)
    private String username;

    @Email(message = "Please input a valid email address")
    @NotBlank(message = "Email address is required")
    @Column(unique = true)
    @JsonView(Views.External.class)
    private String email;

    @NotBlank(message = "Password field is required")
    @Size(min = 8, message = "password must be more than 8 characters")
    @JsonView(Views.Hide.class)
    private String password;

    @Transient
    private String confirmPassword;

    @NotBlank(message = "Phone number is required")
    @Size(min = 11, max = 11, message = "Invalid mobile number")
    @JsonView(Views.External.class)
    private String phoneNumber;

    @JsonView(Views.External.class)
    private String avatarImg;

    @Transient
    private String roleName = "";

    //One-to-many relationship with Post
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonView(Views.Internal.class)
    private List<Post> posts = new ArrayList<>();



    //One-to-Many relationship with Comments
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonView(Views.Internal.class)
    private List<Comment> comments = new ArrayList<>();

    //One-to-Many relationship with Reaction(Likes)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonView(Views.Internal.class)
    private List<Reaction> likes = new ArrayList<>();

    //Role
    //Many-to-One relationship with Role
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonView(Views.Internal.class)
    private Role role;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonView(Views.External.class)
    private Date registered_At;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonView(Views.External.class)
    private Date updated_At;

    @PostLoad
    protected void onLoad(){
        this.fullName = getFirstName() + " "+ getLastName();
        this.roleName = this.role.getRoleName();
    }

    @PrePersist
    public void onRegister(){
        this.registered_At = new Date();
    }

    @PreUpdate
    public void onUpdate(){
        this.updated_At = new Date();
    }

    @PostPersist
    protected void afterRegister(){
        this.onLoad();
    }

    @PostUpdate
    protected void afterUpdate(){
        this.onLoad();
    }

    /*
    userDetails interface method
     */

    @Override
    //@JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("Role_" + this.getRole().getRoleName()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}

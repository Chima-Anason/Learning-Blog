package com.llb.fllbwebsite.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;

    @NotBlank(message = "Firstname is required")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    private String lastName;

    @Transient
    private String fullName;


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
    @Size(min = 8, message = "password must be more than 8 characters")
    private String password;

    @Transient
    private String confirmPassword;

    @NotBlank(message = "Phone number is required")
    @Size(min = 11, max = 11, message = "Invalid mobile number")
    private String phoneNumber;

    private String avatarImg;

    @Transient
    private String roleName = "";

    //One-to-many relationship with Post
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();



    //One-to-Many relationship with Comments
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    //One-to-Many relationship with Reaction(Likes)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reaction> likes = new ArrayList<>();

    //Role
    //Many-to-One relationship with Role
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Role role;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registered_At;

    @JsonFormat(pattern = "yyyy-MM-dd")
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

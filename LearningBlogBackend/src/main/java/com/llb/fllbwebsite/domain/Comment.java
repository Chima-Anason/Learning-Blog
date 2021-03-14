package com.llb.fllbwebsite.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Comment Field cannot be blank ")
    private String content;

    @Transient
    private String userName;

    @Transient
    private String postName;

    //Many-to-One relationship with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;


    //Many-to-One relationship with Post
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Post post;

    //Like - will be implemented in future version

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy- MM-dd")
    private Date created_At;

    @JsonFormat(pattern = "yyyy- MM-dd")
    private Date updated_At;

    @PostLoad
    protected void onLoad(){
        userName = user.getUsername();
        postName = post.getTitle();
    }

    @PrePersist
    public void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    public void onUpdate(){
        this.updated_At = new Date();
    }

}

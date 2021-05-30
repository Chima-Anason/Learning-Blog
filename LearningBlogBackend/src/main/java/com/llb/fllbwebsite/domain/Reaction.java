package com.llb.fllbwebsite.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.External.class)
    private Long id;


    @JsonView(Views.External.class)
    private Boolean isLiked = false;


    @Transient
    private String userName;

    @Transient
    private String postName;

    //Many-to-One relationship with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonView(Views.Internal.class)
    private User user;

    //Many-to-One relationship with Post
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonView(Views.Internal.class)
    private Post post;


    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy- MM-dd")
    @JsonView(Views.External.class)
    private Date created_At;

    @JsonFormat(pattern = "yyyy- MM-dd")
    @JsonView(Views.External.class)
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

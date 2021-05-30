package com.llb.fllbwebsite.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.External.class)
    private Long id;

    @NotBlank(message = "Post title is required")
    @Column(unique = true)
    @JsonView(Views.External.class)
    private String title;

    @NotBlank(message = "Category of Post is required")
    @JsonView(Views.External.class)
    private String categoryName;

    @NotBlank(message = "Post content is required")
    @JsonView(Views.Internal.class)
    private String content;

    @NotBlank(message = "Post cover image is required")
    @JsonView(Views.External.class)
    private String coverImage;

    @Transient
    private String author;

    //Many-to-One relationship with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonView(Views.Internal.class)
    private User user;

    //One-to-Many relationship with Comments
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonView(Views.Internal.class)
    private List<Comment> comments =  new ArrayList<>();

    //One-to-Many relationship with Reaction(Likes)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonView(Views.Internal.class)
    private List<Reaction> likes =  new ArrayList<>();


    //Many-to-One relationship with Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonView(Views.Internal.class)
    private Category category;

    //Avatar

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    @JsonView(Views.External.class)
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonView(Views.External.class)
    private Date updatedAt;

    @PostLoad
    protected void onLoad(){
        author = user.getUsername();
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }


    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}

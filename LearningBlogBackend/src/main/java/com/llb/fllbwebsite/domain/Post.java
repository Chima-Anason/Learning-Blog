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
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Post title is required")
    @Column(unique = true)
    private String title;

    @NotBlank(message = "Category of Post is required")
    private String categoryName;

    @NotBlank(message = "Post content is required")
    private String content;

    @NotBlank(message = "Post cover image is required")
    private String coverImage;

    private String author;

    //Many-to-One relationship with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    //One-to-Many relationship with Comments
    @OneToMany(mappedBy = "post", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments;

    //One-to-Many relationship with Reaction(Likes)
    @OneToMany(mappedBy = "post", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Reaction> likes;


    //Many-to-One relationship with Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Category category;

    //Avatar

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    private Date created_At;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated_At;

    @PrePersist
    protected void onCreate(){
        this.created_At = new Date();
    }


    @PreUpdate
    protected void onUpdate(){
        this.updated_At = new Date();
    }
}

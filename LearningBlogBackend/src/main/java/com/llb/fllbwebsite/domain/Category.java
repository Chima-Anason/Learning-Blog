package com.llb.fllbwebsite.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Category name is required")
    private String categoryName;

    //One-to-Many relationship with post
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Post> posts =  new ArrayList<>();

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created_At;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated_At;

    @PrePersist
    public void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    public void onUpdate(){
        this.created_At = new Date();
    }
}
